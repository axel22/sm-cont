

import scala.util.continuations._


object foreach {
  
  def foreach(f: Int => Unit @suspendable) = {
    var i = 0
    while (i < 10) {
      f(i)
      i += 1
    }
  }
  
  class ForeachIterator(foreachfunc: (Int => Unit @suspendable) => (Unit @suspendable)) extends Iterator[Int] {
    private var current: Int = _
    private var continuation: () => Unit = _
    
    /* Does not work correctly:
    reset {
      foreachfunc { x =>
        shift { (k: Unit => Unit) =>
          continuation = () => {
            continuation = null
            k()
          }
        }
        current = x
      }
    }
    */
    
    reset {
      foreachfunc { x =>
        shift { (k: Unit => Unit) =>
          continuation = () => k()
        }
        current = x
        continuation = null
      }
    }
    
    def hasNext: Boolean = continuation != null
    def next(): Int = {
      continuation()
      current
    }
  }
  
  def main(args: Array[String]) {
    val it = new ForeachIterator(foreach)
    
    while (it.hasNext) println(it.next)
  }
  
  /* HOMEWORK: Implement a foreach that traverses a binary tree (with no parent pointers!). Use that with the `ForeachIterator`. Implement the same binary tree iterator directly. */
  
}









