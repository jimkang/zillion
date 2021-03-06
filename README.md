## Zillion

### Overview

Zillion is a simple Scala package for producing English names for
numbers.

Zillion can currently produce cardinal or ordinal names for all
numbers above -10^3003 and below 10^3003.

```scala
import spire.math.SafeLong
import zillion.{cardinal, ordinal}

cardinal(3)     // "three"
cardinal(101)   // "one hundred one"
cardinal(13579) // "thirteen thousand five hundred seventy-nine"
cardinal(-12)   // "negative twelve"

ordinal(0)      // "zeroth"
ordinal(20)     // "twentieth"
ordinal(117)    // "one hundred seventeenth"
ordinal(9999)   // "nine thousand nine hundred ninety-ninth"

cardinal(SafeLong(10).pow(123) + SafeLong(10).pow(37) + SafeLong(1234))
// "one quadragintallion ten undecillion one thousand two hundred thirty-four"

cardinal(SafeLong(10).pow(300) * 999)
// "nine hundred ninety-nine novenonagintallion"
```

Zillion can also produce cardinal names for fractions and decimal
numbers:

```scala
import spire.math.Rational

cardinal(Rational(14, 27))      // fourteen twenty-sevenths
cardinal.fraction(10, 25)       // ten twenty-fifths
cardinal(BigDecimal("1247.23")) // one thousand two hundred forty-seven and twenty-three hundredths
```

### Getting Zillion

Zillion supports Scala 2.10, 2.11, and 2.12. If you use SBT, you can
include Zillion via the following `build.sbt` snippet:

```
libraryDependencies += "org.spire-math" %% "zillion" % "0.3.0"
```

Zillion also supports Scala.js. To use Zillion with Scala.js, use
the following `build.sbt` snippet:

```
libraryDependencies += "org.spire-math" %%% "zillion" % "0.3.0"
```

### Details

The [strategy](http://en.wikipedia.org/wiki/Names_of_large_numbers#Proposals_for_new_naming_system)
for naming large numbers comes from John Horton Conway and Richard
K. Guy by way of Wikipedia.

This library has not yet been extensively tested or reviewed. I'm not
sure what the best strategy is for unit-testing the very large number
names, other than spot checks and regression tests.

### Future Work

It would be great to support numbers larger than 10^3003.

Right now the the underlying rendering uses `SafeLong`. It might be
worth trying to support `Long` directly (to avoid creating unnecessary
`SafeLong` instances).

It could also be nice to allow pluggable capitalization and grammar
rules. Currently all names are lowercase, and no conjunctions are
used.

Zillion only supports English. I'm not sure how useful this code would
be to rendering number names in other languages. But I'd love to hear
from folks who would be interested in trying to support them.

### Copyright and License

All code is available to you under the MIT license, available at
http://opensource.org/licenses/mit-license.php and also in the
[COPYING](COPYING) file.

Copyright Erik Osheim, 2014-2018.
