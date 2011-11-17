

import scala.util.continuations._



object testlists {
  
  def choice(lo: Int, hi: Int): Int @suspendable = shift {
    (k: Int => Unit) =>
    for (x <- lo until hi) k(x)
  }
  
  def main(args: Array[String]) {
    reset {
      val xs = 0 until choice(0, 100)
      val ys = 0 until choice(0, 100)
      assert(xs.size + ys.size == (xs ++ ys).size, xs + " and " + ys)
    }
    
    reset {
      val xs = (0 until choice(0, 100)) toSet
      val ys = 0 until choice(0, 100)
      assert(xs.size + ys.size == (xs ++ ys).size, xs + " and " + ys)
    }
  }
  
  /* HOMEWORK: Implement the choice operator using the flip operator */
  
}
