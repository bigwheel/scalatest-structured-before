# scalatest-structured-before

This is a expansion of FunSpec of scalatest.

## Installation

Available from maven central.

If you use SBT you can include util-backports in your project with

```scala
libraryDependencies += "com.github.bigwheel" %% "scalatest-structured-before" % "1.0"
```

## Usage

Check [test code](https://github.com/bigwheel/scalatest-structured-before/blob/master/src/test/scala/com/github/bigwheel/scalatest/FunSpecExSpec.scala).

```scala
class FunSpecExSpec extends FunSpecEx with Matchers {

  var subject: Int = _

  describeWithText("description can be used in test") { desc =>
    it ("like this") {
      desc should be("description can be used in test")
    }
  }

  describeWithBefore("When initialized by 0"){ subject = 0 } {
    it("is 0") {
      subject should be(0)
    }

    describeWithBefore("when + 5"){ subject += 5 } {
      it("is 5") {
        subject should be(5)
      }

      describeWithBefore("then - 3"){ subject -= 3 } {
        it("is 2") {
          subject should be(2)
        }
        it("also is even") {
          (2 % 2) should be (0)
        }
      }

      describeWithTextAndBefore("and * -1"){ subject *= -1 } { desc =>
        it("is -5") {
          subject should be(-5)
        }
        it("desc can be used in test") {
          desc should be ("and * -1")
        }
      }
    }

    describe("with normal description") {
      describeWithBefore("works correctly (+ 9)") { subject += 9 } {
        it("is 9") {
          subject should be(9)
        }

        they("`they` also be able to be used") {
          subject should be(9)
        }
      }
    }

  }

}
```
