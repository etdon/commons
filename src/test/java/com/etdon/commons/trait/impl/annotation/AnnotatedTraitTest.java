package com.etdon.commons.trait.impl.annotation;

import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnnotatedTraitTest {

    @Test
    public void isEligible_Present_Success() {

        assertTrue(AnnotatedTrait.of(TestAnnotation.class).isEligible(Inner.class));

    }

    @Test
    public void isEligible_Missing_Failure() throws NoSuchMethodException {

        assertFalse(AnnotatedTrait.of(TestAnnotation.class).isEligible(Inner.class.getDeclaredMethod("method")));

    }

    @TestAnnotation
    private static class Inner {

        public void method() {

        }

    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.TYPE})
    public @interface TestAnnotation {

    }

}
