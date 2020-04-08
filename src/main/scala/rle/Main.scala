package rle

import scala.annotation.tailrec

object RLE {
  def encode(str: String): String = {
    @tailrec
    def loop(rest: String, acc: String): String = {
      if (rest.isEmpty) acc
      else {
        val (head, tail) = rest.span(_ == rest.head)
        val add = (if (head.length > 1) head.length else "") + head.head.toString
        loop(tail, acc + add)
      }
    }

    loop(str, "")
  }

  def decode(str: String): String = {
    @tailrec
    def loop(rest: String, acc: String): String = {
      if (rest.isEmpty) acc
      else {
        val (head, tail) = rest.span(_.isDigit)
        val add = if (head.isEmpty) rest.head.toString else tail.head.toString * head.toInt
        loop(tail.tail, acc + add)
      }
    }

    loop(str, "")
  }
}

object Main extends App {
  val rle = RLE
  def testWithString(test: String): Unit = {
    val encoded = rle.encode(test)
    val decoded = rle.decode (encoded)

    println("Encoded string: " + encoded)
    println("Decoded string: " + decoded)
    println("Original: " + test)
    assert(test == decoded)
    println("-----------------------------------------------------------")
  }

  testWithString("")
  testWithString("ABCD")
  testWithString("AAA")
  testWithString("AAB")
  testWithString("AAABB")
  testWithString("AABBACCCD")
  testWithString("WWWWWWWWWWWWBWWWWWWWWWWWWBBBWWWWWWWWWWWWWWWWWWWWWWWWBWWWWWWWWWWWWWW")


}