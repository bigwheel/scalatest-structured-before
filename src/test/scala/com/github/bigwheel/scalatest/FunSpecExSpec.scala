package com.github.bigwheel.scalatest

import org.scalatest.Matchers

class FunSpecExSpec extends FunSpecEx with Matchers {

  var subject: Int = _

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

      describeWithBefore("and * -1"){ subject *= -1 } {
        it("is -5") {
          subject should be(-5)
        }
      }
    }
  }

}
