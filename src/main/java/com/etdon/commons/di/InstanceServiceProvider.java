package com.etdon.commons.di;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.commons.tuple.ImmutablePair;
import com.etdon.commons.tuple.KeyValuePair;
import com.etdon.commons.util.Exceptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstanceServiceProvider implements ServiceProvider {

    private final Map<ServiceIdentifier, Object> services = new HashMap<>();

    private InstanceServiceProvider(@NotNull final Builder builder) {

        if (builder.selfService) {
            this.register(this);
            this.register(ServiceProvider.class, this);
        }

        for (final KeyValuePair<ServiceIdentifier, Object> pair : builder.servicePairs)
            this.services.put(pair.getKey(), pair.getValue());

    }

    @Override
    public <T> void register(@NotNull final T instance) {

        Preconditions.checkNotNull(instance);
        this.services.put(ServiceIdentifier.of(instance.getClass()), instance);

    }

    @Override
    public <T> void register(@NotNull final Class<T> clazz, @NotNull final T instance) {

        Preconditions.checkNotNull(clazz);
        Preconditions.checkNotNull(instance);
        this.services.put(ServiceIdentifier.of(clazz), instance);

    }

    @Override
    public <T> void register(@NotNull final String identifier, @NotNull final T instance) {

        Preconditions.checkNotNull(instance);
        Preconditions.checkNotNull(instance);
        this.services.put(new ServiceIdentifier(instance.getClass(), identifier), instance);

    }

    @Override
    public <T> void register(@NotNull final Class<T> clazz, @NotNull final String identifier, @NotNull final T instance) {

        Preconditions.checkNotNull(clazz);
        Preconditions.checkNotNull(identifier);
        Preconditions.checkNotNull(instance);
        this.services.put(new ServiceIdentifier(clazz, identifier), instance);

    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T get(@NotNull final Class<T> clazz) {

        Preconditions.checkNotNull(clazz);
        return (T) this.services.get(ServiceIdentifier.of(clazz));

    }

    @SuppressWarnings("unchecked")
    @NotNull
    @Override
    public <T> T getOrThrow(@NotNull final Class<T> clazz) {

        Preconditions.checkNotNull(clazz);
        final Object service = this.services.get(ServiceIdentifier.of(clazz));
        if (service == null)
            throw Exceptional.of(NullPointerException.class, "No service found for class {}.", clazz.getName());

        return (T) service;

    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T get(@NotNull final Class<T> clazz, @NotNull final String identifier) {

        Preconditions.checkNotNull(identifier);
        return (T) this.services.get(new ServiceIdentifier(clazz, identifier));

    }

    @SuppressWarnings("unchecked")
    @NotNull
    @Override
    public <T> T getOrThrow(@NotNull final Class<T> clazz, @NotNull final String identifier) {

        Preconditions.checkNotNull(clazz);
        Preconditions.checkNotNull(identifier);
        final Object service = this.services.get(new ServiceIdentifier(clazz, identifier));
        if (service == null)
            throw Exceptional.of(NullPointerException.class, "No service found for class {} and identifier {}.", clazz.getName(), identifier);

        return (T) service;

    }

    @Override
    public boolean has(@NotNull final Class<?> clazz) {

        Preconditions.checkNotNull(clazz);
        return this.services.containsKey(ServiceIdentifier.of(clazz));

    }

    @Override
    public boolean has(@NotNull final Class<?> clazz, @NotNull final String identifier) {

        Preconditions.checkNotNull(clazz);
        Preconditions.checkNotNull(identifier);
        return this.services.containsKey(new ServiceIdentifier(clazz, identifier));

    }

    public static Builder builder() {

        return new Builder();

    }

    public static class Builder implements FluentBuilder<InstanceServiceProvider> {

        private final List<KeyValuePair<ServiceIdentifier, Object>> servicePairs = new ArrayList<>();
        private boolean selfService = false;

        @NotNull
        public <T> Builder service(@NotNull final T instance) {

            Preconditions.checkNotNull(instance);
            this.servicePairs.add(ImmutablePair.of(ServiceIdentifier.of(instance.getClass()), instance));
            return this;

        }

        @NotNull
        public <T> Builder service(@NotNull final Class<T> clazz, @NotNull final T instance) {

            Preconditions.checkNotNull(clazz);
            Preconditions.checkNotNull(instance);
            this.servicePairs.add(ImmutablePair.of(ServiceIdentifier.of(clazz), instance));
            return this;

        }

        @NotNull
        public <T> Builder service(@NotNull final String identifier, @NotNull final T instance) {

            Preconditions.checkNotNull(identifier);
            Preconditions.checkNotNull(instance);
            this.servicePairs.add(ImmutablePair.of(new ServiceIdentifier(instance.getClass(), identifier), instance));
            return this;

        }

        @NotNull
        public <T> Builder service(@NotNull final Class<T> clazz, @NotNull final String identifier, @NotNull final T instance) {

            Preconditions.checkNotNull(clazz);
            Preconditions.checkNotNull(identifier);
            Preconditions.checkNotNull(instance);
            this.servicePairs.add(ImmutablePair.of(new ServiceIdentifier(clazz, identifier), instance));
            return this;

        }

        @NotNull
        public Builder selfService() {

            this.selfService = true;
            return this;

        }

        @NotNull
        @Override
        public InstanceServiceProvider build() {

            return new InstanceServiceProvider(this);

        }

    }

}
