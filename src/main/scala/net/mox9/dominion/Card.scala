package net.mox9.dominion

sealed trait Coin extends Any {
  def value: Int
  def +(c: Coin): Coin
  def -(c: Coin): Coin
}
object Coin extends (Int => Coin) {
  def apply(n: Int): Coin = CoinImpl(n max 0)
  private final case class CoinImpl(value: Int) extends AnyVal with Coin {
    def +(c: Coin) = Coin(value + c.value)
    def -(c: Coin) = Coin(value - c.value)
  }
}
case class IntWithCoin(private val n: Int) extends AnyVal {
  def coin = Coin(n)
}

sealed trait Vp extends Any {
  def value: Int
  def +(c: Vp): Vp
  def -(c: Vp): Vp
}
object Vp extends (Int => Vp) {
  def apply(n: Int): Vp = VpImpl(n max 0)
  private final case class VpImpl(value: Int) extends AnyVal with Vp {
    def +(vp: Vp) = Vp(value + vp.value)
    def -(vp: Vp) = Vp(value - vp.value)
  }
}
case class IntWithVp(private val n: Int) extends AnyVal {
  def vp = Vp(n)
}

trait Card {
  def cost: Coin
}

sealed trait Treasure extends Card { def value: Coin }

case object Copper extends Treasure { def cost = 0.coin ; def value = 1.coin }
case object Silver extends Treasure { def cost = 3.coin ; def value = 2.coin }
case object Gold   extends Treasure { def cost = 6.coin ; def value = 3.coin }
