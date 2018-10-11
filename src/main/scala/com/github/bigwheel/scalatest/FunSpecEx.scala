package com.github.bigwheel.scalatest

import com.github.bigwheel.scalatest.FunSpecEx._
import org.scalactic._
import org.scalatest._
import scalaz.Scalaz._
import scalaz._

object FunSpecEx {

  private case class DescriptionWithBefore(description: String, before: () => Unit)
  private case class SpecText(value: String)
  private type DescriptionOrSpecText = \/[DescriptionWithBefore, SpecText]


  private implicit class RichTree[T](tree: Tree[T]) {
    def insert(subTree: Tree[T]): Tree[T] = tree.loc.insertDownLast(subTree).toTree
  }

  private implicit class DostTreeLoc(tl: TreeLoc[DescriptionOrSpecText]) {
    def fullQualifiedTestName: String = tl.path.reverse.map {
      case -\/(yyy) => yyy.description
      case \/-(xxx) => xxx.value
    }.tail.mkString(" ") // tail method removes root blank string (see initialization of `var tree`)
  }
}

class FunSpecEx extends FunSpec {

  /**
   * state holding tree for before structuring
   */
  private[this] var tree: Tree[DescriptionOrSpecText] =
    DescriptionWithBefore("", () => ()).left[SpecText].node()

  override protected val it: ItWord = new ItWord() {

    override def apply(specText: String, testTags: Tag*)
      (testFun: => Any /* Assertion */)
      (implicit pos: source.Position): Unit = {
      tree = tree.insert(SpecText(specText).right[DescriptionWithBefore].leaf)
      super.apply(specText, testTags: _*)(testFun)(pos)
    }

  }

  protected[this] def describeWithBefore(description: String)
    (before: => Unit)
    (fun: => Unit)
    (implicit pos: org.scalactic.source.Position): Unit = {
    val backup = tree
    tree = DescriptionWithBefore(description, before _).left[SpecText].node()
    super.describe(description)(fun)(pos)
    tree = backup.insert(tree)
  }

  protected override def runTest(testName: String, args: Args): Status = {
    val targetTestLeaf = tree.loc.find { _.fullQualifiedTestName == testName }.get
    targetTestLeaf.path.reverse.lefts.foreach { _.before() }
    super.runTest(testName, args)
  }

}
