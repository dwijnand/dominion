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
class PlayerCount private (val value: Int) extends AnyVal
object PlayerCount {
  def apply(c: Int) = c sideEffect require(c >= 2 && c <= 4) pipe (new PlayerCount(_))
//def apply(c: Int) = c sideEffect require(c >= 2 && c <= 6) pipe (new PlayerCount(_))
}

// TODO: CardCount.+ ? CardCount.- ?
class CardCount private (val value: Int) extends AnyVal {
  override def toString = s"$value"
//def +(cc: CardCount) = new CardCount(value + cc.value)
}
object CardCount {
  def apply(c: Int) = c sideEffect require(c >= 0) pipe (new CardCount(_))
}
