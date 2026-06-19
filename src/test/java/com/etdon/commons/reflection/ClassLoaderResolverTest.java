package com.etdon.commons.reflection;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @see ClassLoaderResolver
 */
public class ClassLoaderResolverTest {

    @Test
    public void getResolvedClasses_Example_Success() throws IOException, ClassNotFoundException {

        final ClassLoaderResolver classLoaderResolver = ClassLoaderResolver.of(this.getClass().getClassLoader());
        classLoaderResolver.resolve();
        final Set<Class<?>> classes = classLoaderResolver.getResolvedClasses(this.getClass().getPackage().getName());
        assertTrue(classes.contains(this.getClass()));

    }

}
