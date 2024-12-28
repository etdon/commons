package com.etdon.commons.context;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.commons.constant.Constants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MapPlaceholderProcessor extends PlaceholderProcessor {

    private final Map<String, String> placeholders;

    public MapPlaceholderProcessor() {

        this.placeholders = new HashMap<>();

    }

    public MapPlaceholderProcessor(@NotNull final Map<String, String> placeholders) {

        Preconditions.checkNotNull(placeholders);
        this.placeholders = placeholders;

    }

    @Nullable
    public String registerPlaceholder(@NotNull final String identifier, @NotNull final String value) {

        Preconditions.checkNotNull(identifier);
        Preconditions.checkNotNull(value);
        return this.placeholders.put(identifier, value);

    }

    @Nullable
    public String unregisterPlaceholder(@NotNull final String identifier) {

        Preconditions.checkNotNull(identifier);
        return this.placeholders.remove(identifier);

    }

    @NotNull
    @Override
    public String process(@NotNull final String input) {

        Preconditions.checkNotNull(input);
        final char[] chars = input.toCharArray();
        final StringBuilder output = new StringBuilder();

        final StringBuilder identifier = new StringBuilder();
        boolean readingIdentifier = false;

        char previousChar = ' ';
        for (int i = 0; i < chars.length; i++) {
            final char c = chars[i];
            if (c == Constants.ESCAPE_IDENTIFIER) {
                previousChar = c;
                continue;
            }

            if (c == super.getSettings().getPlaceholderIdentifier() && previousChar != Constants.ESCAPE_IDENTIFIER) {
                if (chars.length > i + 1 && chars[++i] == super.getSettings().getValueStartIdentifier())
                    readingIdentifier = true;
            } else if (readingIdentifier && c == super.getSettings().getValueEndIdentifier()) {
                output.append(Optional.ofNullable(this.placeholders.get(identifier.toString()))
                        .orElse(super.createPlaceholder(identifier.toString())));
                identifier.setLength(0);
                readingIdentifier = false;
            } else {
                if (readingIdentifier) {
                    identifier.append(c);
                } else {
                    output.append(c);
                }
            }
            previousChar = c;
        }

        return output.toString();

    }

}
