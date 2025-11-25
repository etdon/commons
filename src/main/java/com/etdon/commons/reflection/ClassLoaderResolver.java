package com.etdon.commons.reflection;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class ClassLoaderResolver {

    public final static String MANIFEST_FILE = "META-INF/MANIFEST.MF";
    public final static char CLASS_CHAR = '$';

    private final ClassLoader classLoader;
    private final Set<String> ignoredPackages = new HashSet<>();

    private final Set<File> processedFiles = new HashSet<>();
    private final Set<String> resolvedClassPaths = new HashSet<>();

    public ClassLoaderResolver(@NotNull final ClassLoader classLoader) {

        this(classLoader, new HashSet<>());

    }

    public ClassLoaderResolver(@NotNull final ClassLoader classLoader,
                               @NotNull final Set<String> ignoredPackages) {

        Preconditions.checkNotNull(classLoader);
        Preconditions.checkNotNull(ignoredPackages);
        this.classLoader = classLoader;
        this.ignoredPackages.addAll(ignoredPackages);

    }

    private ClassLoaderResolver(final Builder builder) {

        this.classLoader = builder.classLoader;
        this.ignoredPackages.addAll(builder.ignoredPackages);

    }

    public void resolve() throws IOException {

        for (final Map.Entry<File, ClassLoader> entry : this.getClassLoaderFiles(this.classLoader).entrySet())
            this.processFile(entry.getKey(), entry.getValue());

    }

    public Set<String> getResolvedClassPaths() {

        final Set<String> classPaths = new HashSet<>();
        for (final String classPath : this.resolvedClassPaths) {
            if (classPath.indexOf(CLASS_CHAR) == -1)
                classPaths.add(classPath);
        }

        return classPaths;

    }

    public Set<String> getResolvedClassPaths(@NotNull final String packageName) {

        Preconditions.checkNotNull(packageName);
        if (this.ignoredPackages.contains(packageName))
            return new HashSet<>();

        final Set<String> classPaths = new HashSet<>();
        for (final String classPath : this.getResolvedClassPaths()) {
            if (this.getPackageName(this.getClassName(classPath)).equals(packageName))
                classPaths.add(classPath);
        }

        return classPaths;

    }

    public Set<String> getResolvedClassPathsDeep(@NotNull final String packageName) {

        Preconditions.checkNotNull(packageName);
        if (this.ignoredPackages.contains(packageName))
            return new HashSet<>();

        final Set<String> classPaths = new HashSet<>();
        for (final String classPath : this.getResolvedClassPaths()) {
            final String className = this.getClassName(classPath);
            if (className.startsWith(packageName) && !this.ignoredPackages.contains(this.getPackageName(className)))
                classPaths.add(classPath);
        }

        return classPaths;

    }

    public Set<Class<?>> getResolvedClasses(final String packageName) throws ClassNotFoundException {

        final Set<String> classPaths = this.getResolvedClassPathsDeep(packageName);
        if (classPaths.isEmpty())
            return new HashSet<>();

        final Set<Class<?>> classes = new HashSet<>();
        for (final String classPath : classPaths)
            classes.add(Class.forName(this.getClassName(classPath), false, classLoader));

        return classes;

    }

    private void processFile(final File file, final ClassLoader classLoader) throws IOException {

        if (!file.exists() || !this.processedFiles.add(file.getCanonicalFile())) return;

        if (file.isDirectory()) {
            this.processDirectory(file, classLoader, "");
        } else {
            this.processJarFile(file, classLoader);
        }

    }

    private void processDirectory(final File directory, final ClassLoader classLoader, final String parent) {

        if (!directory.isDirectory())
            return;

        final File[] files = directory.listFiles();
        if (files == null) return;

        for (final File file : files) {
            if (file.isDirectory()) {
                this.processDirectory(file, classLoader, parent + file.getName() + "/");
            } else {
                final String classPath = parent + file.getName();
                if (classPath.equals(MANIFEST_FILE)) continue;
                this.resolvedClassPaths.add(classPath);
            }
        }

    }

    private void processJarFile(final File file, final ClassLoader classLoader) throws IOException {

        try (final JarFile jarFile = new JarFile(file)) {
            for (final File classFile : this.getJarFileFiles(file, jarFile.getManifest()))
                this.processFile(classFile, classLoader);
            this.resolvedClassPaths.addAll(this.getJarFileClassPaths(jarFile));
        }

    }

    private Set<String> getJarFileClassPaths(final JarFile jarFile) {

        final Set<String> classPaths = new HashSet<>();
        final Enumeration<JarEntry> enumeration = jarFile.entries();
        while (enumeration.hasMoreElements()) {
            final JarEntry entry = enumeration.nextElement();
            if (!entry.isDirectory() && !entry.getName().equals(MANIFEST_FILE))
                classPaths.add(entry.getName());
        }

        return classPaths;

    }

    private Set<File> getJarFileFiles(final File file, final Manifest manifest) throws MalformedURLException {

        final Set<File> classFiles = new HashSet<>();
        if (manifest == null)
            return classFiles;

        final String classPathAttribute = manifest.getMainAttributes().getValue(Attributes.Name.CLASS_PATH.toString());
        if (classPathAttribute == null)
            return classFiles;

        for (final String path : classPathAttribute.split(" ")) {
            final URL url = new URL(file.toURI().toURL(), path);
            if (url.getProtocol().equals("file"))
                classFiles.add(new File(url.getFile()));
        }

        return classFiles;

    }

    private Map<File, ClassLoader> getClassLoaderFiles(final ClassLoader classLoader) throws MalformedURLException {

        final Map<File, ClassLoader> entries = new HashMap<>();
        final ClassLoader parentClassLoader = classLoader.getParent();
        if (parentClassLoader != null)
            entries.putAll(this.getClassLoaderFiles(parentClassLoader));

        for (final URL url : this.getClassLoaderUrls(classLoader)) {
            if (!url.getProtocol().equals("file")) continue;
            final File file = new File(url.getFile());
            entries.putIfAbsent(file, classLoader);
        }

        return entries;

    }

    private List<URL> getClassLoaderUrls(final ClassLoader classLoader) throws MalformedURLException {

        if (classLoader instanceof URLClassLoader) {
            return Arrays.asList(((URLClassLoader) classLoader).getURLs());
        } else if (classLoader.equals(ClassLoader.getSystemClassLoader())) {
            return this.parseClassPath();
        } else {
            // Unknown ClassLoader type.
            return new ArrayList<>();
        }

    }

    private List<URL> parseClassPath() throws MalformedURLException {

        final List<URL> urls = new ArrayList<>();
        for (final String entry : System.getProperty("java.class.path").split(File.pathSeparator)) {
            try {
                urls.add(new File(entry).toURI().toURL());
            } catch (final SecurityException ex) {
                urls.add(new URL("file", null, new File(entry).getAbsolutePath()));
            }
        }

        return urls;

    }

    private String getClassName(final String classPath) {

        return classPath.substring(0, classPath.length() - ".class".length()).replace('/', '.');

    }

    private String getPackageName(final String classPath) {

        final int lastDot = classPath.lastIndexOf('.');
        return lastDot < 0 ? "" : classPath.substring(0, lastDot);

    }

    public static ClassLoaderResolver of(@NotNull final ClassLoader classLoader) {

        return new ClassLoaderResolver(classLoader);

    }

    public static ClassLoaderResolver of(@NotNull final ClassLoader classLoader, @NotNull final Set<String> ignoredPackages) {

        return new ClassLoaderResolver(classLoader, ignoredPackages);

    }

    public static Builder builder() {

        return new Builder();

    }

    public static class Builder implements FluentBuilder<ClassLoaderResolver> {

        private ClassLoader classLoader;
        private final Set<String> ignoredPackages = new HashSet<>();

        private Builder() {

        }

        public Builder classLoader(@NotNull final ClassLoader classLoader) {

            Preconditions.checkNotNull(classLoader);
            this.classLoader = classLoader;
            return this;

        }

        public Builder ignorePackage(@NotNull final String packageName) {

            Preconditions.checkNotNull(packageName);
            this.ignoredPackages.add(packageName);
            return this;

        }

        public Builder ignorePackages(@NotNull final Set<String> ignoredPackages) {

            Preconditions.checkNotNull(ignoredPackages);
            this.ignoredPackages.addAll(ignoredPackages);
            return this;

        }

        @NotNull
        @Override
        public ClassLoaderResolver build() {

            Preconditions.checkNotNull(this.classLoader);
            return new ClassLoaderResolver(this);

        }

    }

}
