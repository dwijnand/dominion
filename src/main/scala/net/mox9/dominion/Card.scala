package net.mox9.dominion

trait Card {
  def cost: Coins
  def types: Seq[CardType]
}

// 60 Copper, 40 Silver, 30 Gold
sealed abstract class BaseTreasure extends Card { def types = Seq(Treasure) }
case object Copper extends BaseTreasure { def cost = 0.coins ; def value = 1.coins }
case object Silver extends BaseTreasure { def cost = 3.coins ; def value = 2.coins }
case object Gold   extends BaseTreasure { def cost = 6.coins ; def value = 3.coins }

// 24 Estates, 12 Dutchies, (12 Gardens), 12 Provinces
sealed abstract class BaseVictoryCard extends Card { def types = Seq(Victory) }
case object Estate   extends BaseVictoryCard { def cost = 2.coins ; def value = 1.vp }
case object Dutchy   extends BaseVictoryCard { def cost = 5.coins ; def value = 3.vp }
case object Province extends BaseVictoryCard { def cost = 8.coins ; def value = 6.vp }

// 2p -> 10, 3p -> 20, 4p -> 30
case object Curse extends Card { def cost = 0.coins ; def types = Seq(CurseK) }
