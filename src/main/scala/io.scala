

import scala.util.continuations._


object io {
  
  trait Blocker {
    var continuation = (c: String) => reset { body(c) }
  }
  
  def main(args: Array[String]) {
  }
  
}
