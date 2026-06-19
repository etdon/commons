package com.etdon.commons.context;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.commons.constant.Constants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MapPlaceholderProcessor extends PlaceholderProcessor {

    private final Map<String, String> placeholders = new HashMap<>();
    private final Map<String, Supplier<String>> valueSuppliers = new HashMap<>();

    public MapPlaceholderProcessor() {

    }

    public MapPlaceholderProcessor(@NotNull final Settings settings) {

        super(settings);

    }

    public MapPlaceholderProcessor(@NotNull final Map<String, String> placeholders) {

        Preconditions.checkNotNull(placeholders);
        this.registerPlaceholders(placeholders);

    }

    public MapPlaceholderProcessor(@NotNull final Settings settings, @NotNull final Map<String, String> placeholders) {

        super(settings);
        Preconditions.checkNotNull(placeholders);
        this.registerPlaceholders(placeholders);

    }

    /**
     * Registers placeholders for all entries of the provided placeholder map.
     *
     * @param placeholders the placeholder map
     */
    public void registerPlaceholders(@NotNull final Map<String, String> placeholders) {

        for (final Map.Entry<String, String> entry : placeholders.entrySet())
            this.registerPlaceholder(entry.getKey(), entry.getValue());

    }

    /**
     * Registers a placeholder with the provided non-<code>null</code> identifier and value.
     *
     * @param identifier the identifier
     * @param value the value
     * @return the previously registered value for the provided identifier or <code>null</code> if there was none
     */
    @Nullable
    public String registerPlaceholder(@NotNull final String identifier, @NotNull final String value) {

        Preconditions.checkNotNull(identifier);
        Preconditions.checkNotNull(value);
        return this.placeholders.put(identifier, value);

    }

    /**
     * Registers a placeholder with the provided non-<code>null</code> identifier and value supplier. During the
     * processing phase value suppliers are invoked to get the actual placeholder value. The value provided by the
     * supplier is <b>not</b> being cached.
     *
     * @param identifier the identifier
     * @param valueSupplier the value supplier
     * @return the previously registered value for the provided identifier or <code>null</code> if there was none
     */
    public String registerPlaceholder(@NotNull final String identifier, @NotNull final Supplier<String> valueSupplier) {

        Preconditions.checkNotNull(identifier);
        Preconditions.checkNotNull(valueSupplier);
        this.valueSuppliers.put(identifier, valueSupplier);
        return this.placeholders.put(identifier, null);

    }

    /**
     * Unregisters the placeholder with the provided non-<code>null</code> identifier if present.
     *
     * @param identifier the identifier
     * @return the previously registered value for the provided identifier or <code>null</code> if there was none
     */
    @Nullable
    public String unregisterPlaceholder(@NotNull final String identifier) {

        Preconditions.checkNotNull(identifier);
        this.valueSuppliers.remove(identifier);
        return this.placeholders.remove(identifier);

    }

    /**
     * Returns the count of currently registered placeholders.
     *
     * @return the placeholder count
     */
    public int getPlaceholderCount() {

        return this.placeholders.size();

    }

    /**
     * Returns a defensive copy of the internal placeholder registry map.
     *
     * @return defensive map copy
     */
    public Map<String, String> getEntries() {

        return new HashMap<>(this.placeholders);

    }

    /**
     * Scans the provided input for placeholder identifiers and replaces them with their registered value or the
     * evaluated value of the registered supplier if present. If a placeholder identifier is found that isn't
     * registered a re-constructed version of the found placeholder identifier created with {@link PlaceholderProcessor#createPlaceholder(String)}
     * will be appended to the output instead.
     *
     * @param input the input
     * @return a new string based on the input with its placeholders replaced
     */
    @NotNull
    @Override
    public String process(@NotNull final String input) {

        Preconditions.checkNotNull(input);
        if (this.placeholders.isEmpty())
            return input;

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

            if (!readingIdentifier && c == super.getSettings().getPlaceholderIdentifier() && previousChar != Constants.ESCAPE_IDENTIFIER) {
                if (chars.length > i + 1 && chars[i + 1] == super.getSettings().getValueStartIdentifier()) {
                    i += 1;
                    readingIdentifier = true;
                } else {
                    output.append(c);
                }
            } else if (readingIdentifier && c == super.getSettings().getValueEndIdentifier()) {
                final String value = this.placeholders.get(identifier.toString());
                if (value != null) {
                    output.append(value);
                } else {
                    final Supplier<String> valueSupplier = this.valueSuppliers.get(identifier.toString());
                    if (valueSupplier != null) {
                        output.append(valueSupplier.get());
                    } else {
                        output.append(super.createPlaceholder(identifier.toString()));
                    }
                }
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
