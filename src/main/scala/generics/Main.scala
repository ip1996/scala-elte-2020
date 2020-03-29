package generics

object Main extends App {
  val cat = new Cat("Cirmos")
  def printMammal(mammal : Mammal): Unit = {
    println("The animal name is: " + mammal.name + " and the fitness: " + mammal.fitness)
  }
  println("The animal name is: " + cat.name + " and the fitness: " + cat.fitness)

  def printCage[A <: Mammal](cage : Cage[A]) = {
    println("The cage contains:")
    cage.animals.foreach((a) => printMammal(a))
  }

  val cat2 = new Cat("Dormi")
  val dog = new Dog("Morzsa")

  printMammal(cat2)
  printMammal(dog)
  //val animal_cage = new Cage[Animal](5) not possible because Mammal is the upper bound
  val cat_cage = new Cage[Cat](2)
  val dog_cage = new Cage[Dog](3)

  cat_cage.AddAnimal(cat)
  cat_cage.AddAnimal(cat2)

  printCage(cat_cage)

  //val human_food = new Food[Human]
  //val cat_food = new Food[Cat] and connection amongst the type restrictions
  //cat_cage.AddAnimal(dog) not possible because of type constraint

}


