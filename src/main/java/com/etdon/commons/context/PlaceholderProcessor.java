package com.etdon.commons.context;

import com.etdon.commons.builder.FluentBuilder;
import com.etdon.commons.conditional.Preconditions;
import com.etdon.commons.constant.Constants;
import org.jetbrains.annotations.NotNull;

public abstract class PlaceholderProcessor {

    private static final Settings DEFAULT_SETTINGS = Settings.builder()
            .placeholderIdentifier(Constants.DEFAULT_PLACEHOLDER_IDENTIFIER)
            .valueStartIdentifier(Constants.DEFAULT_VALUE_START_IDENTIFIER)
            .valueEndIdentifier(Constants.DEFAULT_VALUE_END_IDENTIFIER)
            .build();

    private final Settings settings;

    public PlaceholderProcessor() {

        this.settings = DEFAULT_SETTINGS;

    }

    public PlaceholderProcessor(@NotNull final Settings settings) {

        Preconditions.checkNotNull(settings);
        this.settings = settings;

    }

    protected String createPlaceholder(@NotNull final String identifier) {

        return new StringBuilder()
                .append(this.settings.getPlaceholderIdentifier())
                .append(this.settings.getValueStartIdentifier())
                .append(identifier)
                .append(this.settings.getValueEndIdentifier())
                .toString();

    }

    @NotNull
    public abstract String process(@NotNull final String input);

    public Settings getSettings() {

        return this.settings;

    }

    public static class Settings {

        private final char placeholderIdentifier;
        private final char valueStartIdentifier;
        private final char valueEndIdentifier;

        private Settings(@NotNull final Builder builder) {

            this.placeholderIdentifier = builder.placeholderIdentifier;
            this.valueStartIdentifier = builder.valueStartIdentifier;
            this.valueEndIdentifier = builder.valueEndIdentifier;

        }

        public char getPlaceholderIdentifier() {

            return this.placeholderIdentifier;

        }

        public char getValueStartIdentifier() {

            return this.valueStartIdentifier;

        }

        public char getValueEndIdentifier() {

            return this.valueEndIdentifier;

        }

        public static Builder builder() {

            return new Builder();

        }

        public static final class Builder implements FluentBuilder<Settings> {

            private Character placeholderIdentifier;
            private Character valueStartIdentifier;
            private Character valueEndIdentifier;

            private Builder() {

            }

            @NotNull
            public Builder placeholderIdentifier(final char placeholderIdentifier) {

                this.placeholderIdentifier = placeholderIdentifier;
                return this;

            }

            @NotNull
            public Builder valueStartIdentifier(final char valueStartIdentifier) {

                this.valueStartIdentifier = valueStartIdentifier;
                return this;

            }

            @NotNull
            public Builder valueEndIdentifier(final char valueEndIdentifier) {

                this.valueEndIdentifier = valueEndIdentifier;
                return this;

            }

            @NotNull
            @Override
            public Settings build() {

                Preconditions.checkNotNull(this.placeholderIdentifier);
                Preconditions.checkNotNull(this.valueStartIdentifier);
                Preconditions.checkNotNull(this.valueEndIdentifier);

                return new Settings(this);

            }

        }

    }

}
