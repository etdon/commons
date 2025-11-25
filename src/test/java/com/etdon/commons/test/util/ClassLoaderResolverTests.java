package com.etdon.commons.test.util;

import com.etdon.commons.reflection.ClassLoaderResolver;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClassLoaderResolverTests {

    @Test
    public void getResolvedClasses_Example_Success() throws IOException, ClassNotFoundException {

        final ClassLoaderResolver classLoaderResolver = ClassLoaderResolver.of(this.getClass().getClassLoader());
        classLoaderResolver.resolve();
        final Set<Class<?>> classes = classLoaderResolver.getResolvedClasses(this.getClass().getPackage().getName());
        assertTrue(classes.contains(this.getClass()));

    }

}
