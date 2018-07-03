package zillion

import org.scalatest._
import prop._
import org.scalacheck.{Arbitrary, Gen}
import Arbitrary.arbitrary
import spire.math.SafeLong

case class Exponent(value: Int) {
  require(0 <= value && value <= 3003)
}

object Exponent {
  implicit val arbitraryExponent: Arbitrary[Exponent] = {
    //val upper = 3003 // scala.js currently blows up with this
    val upper = 300
    Arbitrary(Gen.choose(0, upper).map(Exponent(_)))
  }
}

class CardinalTest extends GenericTest {
  def render(n: SafeLong): String = cardinal(n)
}

class OrdinalTest extends GenericTest {
  def render(n: SafeLong): String = ordinal(n)
}

class AlphanumericTest extends FlatSpec {
  "10698734" should "work like int render" in {
    assert(cardinal("10698734") == "ten million six hundred ninety-eight thousand seven hundred thirty-four")
  }
 "1h" should "get you aitchteen" in {
    assert(cardinal("1h") == "aitchteen")
  }
}

trait GenericTest extends PropSpec with Matchers with PropertyChecks {

  implicit lazy val arbitrarySafeLong: Arbitrary[SafeLong] =
    Arbitrary(arbitrary[BigInt].map(SafeLong(_)))

  def render(n: SafeLong): String

  def checkModK(n: SafeLong, k: Int) {
    val m = n.abs % k
    if (m != 0) render(n).endsWith(render(m)) shouldBe true
  }

  property("big numbers don't crash") {
    forAll { (k: Exponent, offset: SafeLong, b: Boolean) =>
      val big = SafeLong(10).pow(k.value)
      render(big - offset)
      render(offset - big)
    }
  }

  property("negative numbers are consistent") {
    forAll { (n: SafeLong) =>
      val (x, y) = if (n < 0) (-n, n) else (n, -n)
      val sx = render(x)
      val sy = render(y)
      if (sx == sy) n shouldBe 0
      else {
        sy.startsWith("negative ") shouldBe true
        sy.substring(9) shouldBe sx
      }
    }
  }

  property("last digit") {
    forAll { (n: SafeLong) =>
      val m = n.abs % 100
      if (m < 10 || m > 20) checkModK(n, 10)
    }
  }

  property("last two digits") {
    forAll { (n: SafeLong) => checkModK(n, 100) }
  }

  property("last three digits") {
    forAll { (n: SafeLong) => checkModK(n, 1000) }
  }

  property("Int and SafeLong match") {
    forAll { (n: Int) => render(n) shouldBe render(SafeLong(n)) }
  }
}
