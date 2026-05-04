This package introduces the `Explorer` type and the associated concept of state snapshots that users of the API can
take advantage of. It aims to reduce code repetition while providing a safety rail and increasing the user-friendliness
of implementing APIs.

When working with APIs that maintain a constant state flow it's very likely that users eventually end up in scenarios
where they have to temporarily store/cache the value of one or more components with the intention of restoring the
original value(s) after performing certain actions. Two common examples for this are byte readers and drawing APIs like
`Graphics`.

In the latter drawing a black background followed by white text saying `Hello World!` requires the user to change the
intended color using `#setColor` once for the initial background color and once for the text.
```java
final Graphics2D graphics2D = ...;
graphics2D.setColor(Color.BLACK);
graphics2D.fillRect(0, 0, width - 1, height - 1);
graphics2D.setColor(Color.WHITE);
graphics2D.drawString("Hello World!", x, y);
graphics2D.dispose();
```
If the background color is, for example, unknown because it can be configured by the user restoring it before for
continuing to draw with it is necessary:
```java
final Color previousColor = graphics2D.getColor();
graphics2D.setColor(fancyNewColor);
...
graphics2D.setColor(previousColor);
```
While this is a very primitive and not realistic example only covering a single component it should suffice to
illustrate the issue. For a byte reader a similar scenario could feature two components like the offset and byte order
while other examples could theoretically feature a lot more components.

`Explorer` implementations utilize the behavior of `AutoCloseable` to create scopes that allow the user to change one
or multiple states while restoring the original states once out-of-scope.

Coming back to the byte reader example an implementation utilizing an `Explorer` could look like this:
```java
try (final ByteReader.Explorer explorer = byteReader.explore(ByteOrder.BIG_ENDIAN, 100)) {
    final int bigEndianInteger = byteReader.readInteger();
}
```