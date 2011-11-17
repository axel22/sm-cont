

import scala.util.continuations._


object nfa {
  
  sealed trait Regex
  /* c */
  case class Character(c: Char) extends Regex
  /* r1 r2 */
  case class Concat(r1: Regex, r2: Regex) extends Regex
  /* r1 + r2 */
  case class Choice(r1: Regex, r2: Regex) extends Regex
  /* r* */
  case class Repeat(r: Regex) extends Regex
  
  def parse(r: Regex, s: List[Char]) = r match {
    case Character(c) =>
    case Concat(r1, r2) =>
    case Choice(r1, r2) =>
    case Repeat(r) =>
  }
  
  def main(args: Array[String]) {
    
  }
  
}
