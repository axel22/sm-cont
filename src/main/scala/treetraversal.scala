

import scala.util.continuations._


object treetraversal {
  
  def flip: Boolean @suspendable = shift {
    (k: Boolean => Unit) =>
    k(true)
    k(false)
  }
  
  case class Node(v: Int, left: Node = null, right: Node = null)
  
  def traverse(n: Node): Unit @suspendable = if (n != null) {
    println(n.v)
    if (flip) traverse(n.left) else traverse(n.right)
  }
  
  def main(args: Array[String]) {
    val tree = Node(
      2,
      Node(1),
      Node(3)
    )
    
    reset {
      traverse(tree)
    }
  }
  
}
