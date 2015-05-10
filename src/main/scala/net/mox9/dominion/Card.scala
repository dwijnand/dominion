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

sealed trait VPoint extends Any {
  def value: Int
  def +(c: VPoint): VPoint
  def -(c: VPoint): VPoint
}
object VPoint extends (Int => VPoint) {
  def apply(n: Int): VPoint = VPointImpl(n max 0)
  private final case class VPointImpl(value: Int) extends AnyVal with VPoint {
    def +(p: VPoint) = VPoint(value + p.value)
    def -(p: VPoint) = VPoint(value - p.value)
  }
}
case class IntWithVp(private val n: Int) extends AnyVal {
  def vp = VPoint(n)
}

trait Card {
  def cost: Coin
}

sealed trait TreasureCard extends Card { def value: Coin }

case object Copper extends TreasureCard { def cost = 0.coin ; def value = 1.coin }
case object Silver extends TreasureCard { def cost = 3.coin ; def value = 2.coin }
case object Gold   extends TreasureCard { def cost = 6.coin ; def value = 3.coin }

sealed trait VictoryCard extends Card { def value: VPoint }

case object Estate   extends VictoryCard { def cost = 2.coin ; def value = 1.vp }
case object Dutchy   extends VictoryCard { def cost = 5.coin ; def value = 3.vp }
case object Province extends VictoryCard { def cost = 8.coin ; def value = 6.vp }
