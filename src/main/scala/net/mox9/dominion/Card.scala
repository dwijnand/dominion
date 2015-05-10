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

case class VPoint(value: Int) extends AnyVal {
  def +(p: VPoint) = VPoint(value + p.value)
  def -(p: VPoint) = VPoint(value - p.value)
}

trait Card {
  def cost: Coin
}

// 60 Copper, 40 Silver, 30 Gold
sealed trait TreasureCard extends Card { def value: Coin }

case object Copper extends TreasureCard { def cost = 0.coin ; def value = 1.coin }
case object Silver extends TreasureCard { def cost = 3.coin ; def value = 2.coin }
case object Gold   extends TreasureCard { def cost = 6.coin ; def value = 3.coin }

// 24 Estates, 12 Dutchies, (12 Gardens), 12 Provinces
sealed trait VictoryCard extends Card { def value: VPoint }

case object Estate   extends VictoryCard { def cost = 2.coin ; def value = 1.vp }
case object Dutchy   extends VictoryCard { def cost = 5.coin ; def value = 3.vp }
case object Province extends VictoryCard { def cost = 8.coin ; def value = 6.vp }

// 2p -> 10, 3p -> 20, 4p -> 30
case object Curse extends Card { def cost = 0.coin ; def value = (-1).vp }
