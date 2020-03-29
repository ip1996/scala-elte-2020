package structural_types

trait StructType[A <: {def func[A,B](a:A, b : B) : Unit}] {

}

trait ValueStrictedType[A <: {val x : Int}] { // lower bound doesn't work

}

class ValClass {
  val x : Int = 42
}

class SameType {
  def func[C, D](c : C, d: D): Unit = {
    println("Do nothing")
  }
  def placeHolder = println("Placeholder")
}



object Test extends App {
  def check(structType: StructType[SameType]): Unit = {
    println("The type structure is correct")
  }

  def valCheck(valueType : ValueStrictedType[ValClass]): Unit = {
    println("The value type structure is correct")
  }

  check(new StructType[SameType] {})
  valCheck(new ValueStrictedType[ValClass] {})
}
