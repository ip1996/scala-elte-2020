package binarytree

object Main extends App {
  sealed trait Tree
  case class Leaf(val value: Int) extends Tree
  case class Node(val left : Tree, val right: Tree, val value: Int) extends Tree

  val smallTre = Node(
          Node(Leaf(3), Leaf(2), 4),
          Leaf(6),
          0
        )

  def traverse(t : Tree) {
    t match {
      case l : Leaf => print(l.value + " ")
      case n : Node => {
        traverse(n.left)
        traverse(n.right)
        print(n.value + " ")
      }
    }
  }

  traverse(smallTre)
}
