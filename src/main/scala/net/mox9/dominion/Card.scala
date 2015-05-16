package net.mox9.dominion

trait Series { def kingdomCards: Seq[KingdomCard] }

sealed trait CardType
case object Action   extends CardType
case object Attack   extends CardType
case object CurseK   extends CardType
case object Reaction extends CardType
case object Treasure extends CardType
case object Victory  extends CardType

sealed trait CardKind
case object Basic   extends CardKind
case object Kingdom extends CardKind

trait Card {
  def kind: CardKind
  def cost: Coins
  def types: Seq[CardType]
}

abstract class BasicCard   extends Card { def kind = Basic }
abstract class KingdomCard extends Card { def kind = Kingdom ; def series: Series }

abstract class TreasureCard extends BasicCard { def types = Seq(Treasure) }
case object Copper extends TreasureCard { def series = S00Base ; def cost = 0.coins ; def value = 1.coins }
case object Silver extends TreasureCard { def cost = 3.coins ; def value = 2.coins }
case object Gold   extends TreasureCard { def cost = 6.coins ; def value = 3.coins }

sealed abstract class VictoryCard extends BasicCard { def types = Seq(Victory) }
case object Estate   extends VictoryCard { def cost = 2.coins ; def value = 1.vps }
case object Dutchy   extends VictoryCard { def cost = 5.coins ; def value = 3.vps }
case object Province extends VictoryCard { def cost = 8.coins ; def value = 6.vps }

case object Curse extends BasicCard { def cost = 0.coins ; def types = Seq(CurseK) }
