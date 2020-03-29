package generics

class Male(override val name : String) extends Human(name) {
  override def fitness: Int = 30
}
