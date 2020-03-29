package generics

import scala.collection.mutable.ListBuffer

class Cage[A <: Mammal](size : Int) {
  var animals : ListBuffer[A] = ListBuffer()

  def AddAnimal(mammal : A) ={
    if (animals.size < size) {
      animals += mammal
    }
  }

}
