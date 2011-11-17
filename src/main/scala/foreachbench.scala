

import scala.util.continuations._
import scala.testing.Benchmark


object ContinuationIteratorBench extends Benchmark {
  
  def foreach(f: Int => Unit @suspendable) = {
    var i = 0
    while (i < 1000000) {
      f(i)
      i += 1
    }
  }
  
  class ForeachIterator(foreachfunc: (Int => Unit @suspendable) => (Unit @suspendable)) extends Iterator[Int] {
    private var current: Int = _
    private var continuation: () => Unit = _
    
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
  
  def run() {
    var sum = 0
    val it = new ForeachIterator(foreach)
    while (it.hasNext) sum += it.next
  }
  
}


object IteratorBench extends Benchmark {
  
  def run() {
    val it = new Iterator[Int] {
      var i = -1
      def hasNext = i < 1000000
      def next() = {
        i += 1
        i
      }
    }
    
    var sum = 0
    while (it.hasNext) sum += it.next()
  }
  
}



