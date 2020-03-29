package generics

class Cat(override val name : String) extends Felid(name) {
  override def fitness: Int = 9
}
