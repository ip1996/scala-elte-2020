package ko_kontra_variant

import generics.{Cat, Felid, PersianCat}

object Main extends App {
  val cat : Cat = new Cat("Cirmos")

  def printMyCat(printer: ContraPrinter[Cat]): Unit = {
    printer.print(cat)
  }

  printMyCat(new CatContraPrinter)
  printMyCat(new MammalContraPrinter)
  //printMyCat(new PersianCatPrinter) not working because it's contravariant

  def myCatGenerator(generator : CoGenerator[Cat]) : Cat = {
    generator.generate()
  }

  //val felid : Cat = myCatGenerator(new FelidGenerator) not working because type is covariant
  val cat2 : Cat = myCatGenerator(new CatGenerator)
  val persianCat : Cat = myCatGenerator(new PersianCatGenerator)

}
