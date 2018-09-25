package com.github.bigwheel.scalatest

import org.scalatest._
import org.scalactic._

import scalaz._
import Scalaz._

class FunSpecEx extends FunSpec {

  private[this] type Description = (String, () => Unit)
  private[this] type DescriptionOrTestTitle = \/[Description, String]
  private[this] var tree: Tree[DescriptionOrTestTitle] = ("", () => ()).left[String].node()

  override protected val it: ItWord = new ItWord() {
    override def apply(specText: String, testTags: Tag*)(testFun: => Any /* Assertion */)(implicit pos: source.Position): Unit = {
      tree = tree.loc.insertDownLast(\/-(specText).asInstanceOf[DescriptionOrTestTitle].leaf).toTree
      super.apply(specText, testTags: _*)(testFun)(pos)
    }
  }

  protected[this] def describeWithBefore(description: String)
    (before: => Unit)
    (fun: => Unit)
    (implicit pos: org.scalactic.source.Position): Unit = {
    val backup = tree
    tree = (description, before _).left[String].node()
    super.describe(description)(fun)(pos)
    tree = backup.loc.insertDownLast(tree).toTree
  }

  protected override def runTest(testName: String, args: Args): Status = {
    val targetTestLeaf: TreeLoc[DescriptionOrTestTitle] = tree.loc.find { treeLoc =>
      val strOfDescriptionAndTest = treeLoc.path.reverse.map {
        case -\/(yyy) => yyy._1
        case \/-(xxx) => xxx
      }
      strOfDescriptionAndTest.tail.mkString(" ") == testName
    }.get
    val descriptions: Seq[DescriptionOrTestTitle] = targetTestLeaf.path.reverse
    val befores: Seq[() => Unit] = descriptions.map {
      case -\/(yyy) => yyy._2
      case \/-(_) => () => ()
    }
    for (before <- befores) before()
    super.runTest(testName, args)
  }

}
