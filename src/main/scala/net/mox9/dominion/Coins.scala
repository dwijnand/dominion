package net.mox9.dominion

class Coins private (val value: Int) extends AnyVal {
  def +(c: Coins): Coins = Coins(value + c.value)
  override def toString = s"Coins($value)"
}
object Coins extends (Int => Coins) {
  def apply(n: Int): Coins = new Coins(n max 0)
}
