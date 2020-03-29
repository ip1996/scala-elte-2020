package generics

class Dog(override val name : String) extends Mammal(name) {
  override def fitness: Int = 5
}
