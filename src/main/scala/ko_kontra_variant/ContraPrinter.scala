package ko_kontra_variant

import generics.{Animal, Cat, Dog, Mammal, PersianCat}
;


trait ContraPrinter [-A] {
  def print(value : A): Unit
}

class MammalContraPrinter extends ContraPrinter[Mammal] {
  override def print(value: Mammal): Unit = {
    println("The mammal name is: " + value.name + " and the fitness is: " + value.fitness)
  }
}

class CatContraPrinter extends ContraPrinter[Cat] {
  override def print(value: Cat): Unit = {
    println("The cat name is: " + value.name + " and the fitness is: " + value.fitness)
  }
}

class PersianCatContraPrinter extends ContraPrinter[PersianCat] {
  override def print(value: PersianCat): Unit = {
    println("The persianCat name is: " + value.name + " and the fitness is: " + value.fitness)
  }
}
