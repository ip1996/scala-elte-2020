package generics

class Female(override val name : String) extends Human(name) {
  override def fitness: Int = 30
}
