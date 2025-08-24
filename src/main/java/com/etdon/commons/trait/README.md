This package introduces the `Trait` type and the associated concept of declaring a check for characteristics that a
certain type might have. This leads to a much more flexible and user-extensible API.

If we're taking a look at the apache commons `StringUtils` class it's easy to see that uber utility classes like that
end up being extremely bloated, hard to read and hard to navigate. At the time of writing this the class has over 9000
lines including comments.

A lot of the actual components can be sorted into specific categories. The class for example features well over 20
methods that check for specific traits a provided `CharSequence` might have. All of those checker methods share very
similar characteristics: They take a `CharSequence` input and return a `boolean`. This is a pattern you can see
repeated in countless other utility classes of the library.

All of those checker methods could be flattened into one checker method instead: `#checkTraits` which takes a nullable
input (for example in the form of a `CharSequence`) as well as a variable array of nullable `Trait` instances.

This approach provides various advantages:
- De-bloating of utility classes.
- More convenience when checking for multiple traits.
- Much more user-extensible because custom traits can be provided.
- Simple traits can easily be represented by a lambda expression.

Comparing the two approaches in a practical example where you'd, for example, check if a `String` is not blank,
alphanumeric and mixed case you might end up with something like this:
```java
final String input = "EXAMPLE123";
return StringUtils.isNotBlank(input) &&
            StringUtils.isAlphanumeric(input) &&
            StringUtils.isMixedCase(input);
```
while the proposed concept could turn it into something like this:
```java
final String input = "EXAMPLE123";
return Strings.checkTraits(input, StringTrait.NOT_BLANK, StringTrait.ALPHANUMERIC, StringTrait.MIXED_CASE);
```
or this with static imports:
```java
import static com.etdon.commons.trait.impl.string.StringTrait.*;
...
final String input = "EXAMPLE123";
return Strings.checkTraits(input, NOT_BLANK, ALPHANUMERIC, MIXED_CASE);
```
Because `Trait` instances are declared as constants they can also be used to perform checks directly reducing the
complexity of a regular `#checkTraits` call:
```java
final String input = "EXAMPLE123";
return StringTrait.NOT_BLANK.isEligible(input);
```
The naming is not final and might change overtime, that's true for both the name `Trait` as well as any method names
related. Adding more power to trait checkers could be considered as well, another category of similar but slightly
different checkers are methods like `#startsWith`. The primary difference here is the fact that they require additional
context (something to check for) in order to work.

An experimental `Function` type wrapping a static `Trait` factory can be found in `StringTrait` in the form of
`CONTAINS`, the usage looks like this at the moment:
```java
final String input = "EXAMPLE123";
final String target = "123";
return Strings.checkTraits(input, StringTrait.CONTAINS.apply(target));
```
It's unsure if this or something like this will remain in the library, the idea of further flattening utility methods
like `#containsAny` or `#endsWith` sounds tempting. There are multiple things to consider here though including - but
not limited to - safety, performance, user-end complexity, flexibility and visual appeal. Static factory methods like
`StringTrait.contains(target)` could be used instead to reduce user-end complexity and increase visual appeal at the
cost of reduced flexibility since users wouldn't be able to add an equivalent for custom checks.