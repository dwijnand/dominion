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

sealed trait Treasure extends Card { def value: Coin }

case object Copper extends Treasure { def cost = 0.coin ; def value = 1.coin }
case object Silver extends Treasure { def cost = 3.coin ; def value = 2.coin }
case object Gold   extends Treasure { def cost = 6.coin ; def value = 3.coin }
