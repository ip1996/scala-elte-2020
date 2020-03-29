package generics

class Felid(override val name: String) extends Mammal(name) {
  override def fitness: Int = 7
}
