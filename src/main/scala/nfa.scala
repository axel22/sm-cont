

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
  
  // List[Char] may be a non-empty list, an empty list or a null
  // - an empty list denotes that there is nothing else in the input
  // - a non-empty denotes that there is more stuff in the input
  // - null denotes that a continuation couldn't parse the input
  
  def flip: Boolean @cpsParam[List[Char], List[Char]] = shift {
    (k: Boolean => List[Char]) =>
    val heads = k(true)
    if (heads == null) k(false)
    else heads
  }
  
  def continue(lst: List[Char]): List[Char] @cpsParam[List[Char], List[Char]] = shift {
    (k: List[Char] => List[Char]) => k(lst)
  }
  
  def parse(r: Regex, s: List[Char]): List[Char] @cpsParam[List[Char], List[Char]] = r match {
    case Character(c) => s match {
      case `c` :: xs => xs
      case _ => null
    }
    case Choice(r1, r2) => if (flip) parse(r1, s) else parse(r2, s)
    case Concat(r1, r2) =>
      val rest = parse(r1, s)
      if (rest == null) continue(null)
      else parse(r2, rest)
    case Repeat(r) =>
      if (flip) continue(s)
      else {
        val rest = parse(r, s)
        if (rest == null) continue(null)
        else parse(Repeat(r), rest)
      }
  }
  
  def main(args: Array[String]) {
    implicit def char2regex(c: Char) = Character(c)
    val s = "aaaaaaaaaac".toList
    val r = Concat(Repeat('a'), Choice('b', 'c'))
    
    val result = reset {
      parse(r, s)
    }
    println(result)
    println(result == Nil)
  }
  
}
