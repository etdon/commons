package com.etdon.commons.di;

import com.etdon.commons.conditional.Preconditions;
import org.jetbrains.annotations.NotNullByDefault;

import java.util.Objects;

/**
 * Identifies a registered service by its class and an identifier.
 */
@NotNullByDefault
public class ServiceIdentifier {

    private final Class<?> clazz;
    private final String identifier;

    public ServiceIdentifier(final Class<?> clazz) {

        this.clazz = clazz;
        this.identifier = clazz.getName();

    }

    public ServiceIdentifier(final Class<?> clazz,
                             final String identifier) {

        this.clazz = clazz;
        this.identifier = identifier;

    }

    @Override
    public String toString() {

        return "ServiceIdentifier{" +
                "clazz=" + clazz +
                ", identifier='" + identifier + '\'' +
                '}';

    }

    @Override
    public int hashCode() {

        return Objects.hash(clazz, identifier);

    }

    @Override
    public boolean equals(final Object input) {

        if (this == input) return true;
        if (input == null || getClass() != input.getClass()) return false;

        final ServiceIdentifier serviceIdentifier = (ServiceIdentifier) input;
        return clazz.equals(serviceIdentifier.clazz) && identifier.equals(serviceIdentifier.identifier);

    }

    public static ServiceIdentifier of(final Class<?> clazz) {

        Preconditions.checkNotNull(clazz);
        return new ServiceIdentifier(clazz);

    }

}
