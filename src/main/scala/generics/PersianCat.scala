package generics

class PersianCat(override val name: String) extends Cat(name) {
  override def fitness: Int = 12
}
