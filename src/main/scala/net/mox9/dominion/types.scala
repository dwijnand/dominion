package net.mox9.dominion

case class Actions(value: Int) extends AnyVal {
  def +(a: Actions) = Actions(value + a.value)
}
case class Buys(value: Int) extends AnyVal {
  def +(b: Buys) = Buys(value + b.value)
}
case class Coins(value: Int) extends AnyVal {
  def +(c: Coins) = Coins(value + c.value)
}
case class VPoints(value: Int) extends AnyVal {
  def +(p: VPoints) = VPoints(value + p.value)
}

// TODO: Kill PlayerCount?
sealed trait PlayerCount { def value: Int ; override def toString = s"$value" }
case object _2P extends PlayerCount { def value = 2 }
case object _3P extends PlayerCount { def value = 3 }
case object _4P extends PlayerCount { def value = 4 }
//case object _5P extends PlayerCount { def value = 5 }
//case object _6P extends PlayerCount { def value = 6 }

// TODO: CardCount.+ ? CardCount.- ?
class CardCount private (val value: Int) extends AnyVal {
  override def toString = s"$value"
//def +(cc: CardCount) = new CardCount(value + cc.value)
}
object CardCount extends (Int => CardCount) {
  def apply(c: Int) = c sideEffect require(c >= 0) pipe (new CardCount(_))
}
