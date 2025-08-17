package com.etdon.commons.builder;

/**
 * Builder interface for fluent builders that allow chaining. The interface itself can not validate the honesty of
 * implementing classes. It is up to the developer to select the proper Builder interface for their needs, for a
 * regular, non-fluent, builder consider using {@link com.etdon.commons.builder.Builder} instead.
 *
 * @param <T> The target type.
 */
@FunctionalInterface
public interface FluentBuilder<T> extends Builder<T> {
}
