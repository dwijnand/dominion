package net.mox9.dominion

sealed trait Coin extends Any {
  def value: Int
  def +(c: Coin): Coin
  def -(c: Coin): Coin
}
object Coin extends (Int => Coin) {
  def apply(n: Int): Coin = CoinImpl(n max 0)
  private final case class CoinImpl(value: Int) extends Coin {
    def +(c: Coin) = Coin(value + c.value)
    def -(c: Coin) = Coin(value - c.value)
  }
}
case class IntWithCoin(private val n: Int) extends AnyVal {
  def coin = Coin(n)
}

trait Card {
  def cost: Coin
}
