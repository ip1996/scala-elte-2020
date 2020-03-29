package ko_kontra_variant

import generics.{Cat, Felid, PersianCat}

trait CoGenerator[+A] {
  def generate() : A
}


class FelidGenerator extends CoGenerator[Felid] {
  override def generate(): Felid = new Felid("Felid")
}

class CatGenerator extends CoGenerator[Cat] {
  override def generate(): Cat = new Cat("Cirmus")
}

class PersianCatGenerator extends CoGenerator[PersianCat] {
  override def generate(): PersianCat = new PersianCat("Hopehely")
}