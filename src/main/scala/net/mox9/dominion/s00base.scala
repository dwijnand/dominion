package net.mox9.dominion

// 60 Copper, 40 Silver, 30 Gold
sealed abstract class BaseTreasure extends Card { def types = Seq(Treasure) }
case object Copper extends BaseTreasure { def cost = 0.coin ; def value = 1.coin }
case object Silver extends BaseTreasure { def cost = 3.coin ; def value = 2.coin }
case object Gold   extends BaseTreasure { def cost = 6.coin ; def value = 3.coin }

// 24 Estates, 12 Dutchies, (12 Gardens), 12 Provinces
sealed abstract class BaseVictoryCard extends Card { def types = Seq(Victory) }
case object Estate   extends BaseVictoryCard { def cost = 2.coin ; def value = 1.vp }
case object Dutchy   extends BaseVictoryCard { def cost = 5.coin ; def value = 3.vp }
case object Province extends BaseVictoryCard { def cost = 8.coin ; def value = 6.vp }

// 2p -> 10, 3p -> 20, 4p -> 30
case object Curse extends Card { def cost = 0.coin ; def types = Seq(CurseK) }

case object Cellar extends Card {
  def cost = 2.coin
  def types = Seq(Action)
}

case object Chapel extends Card {
  def cost = 2.coin
  def types = Seq(Action)
}

case object Moat extends Card {
  def cost = 2.coin
  def types = Seq(Action, Reaction)
}

case object Chancellor extends Card {
  def cost = 3.coin
  def types = Seq(Action)
}

case object Village extends Card {
  def cost = 3.coin
  def types = Seq(Action)
  // +1 Card +2 Action
}

case object Woodcutter extends Card {
  def cost = 3.coin
  def types = Seq(Action)
  // +1 Buy +2 Coin
}

case object Workshop extends Card {
  def cost = 3.coin
  def types = Seq(Action)
}

case object Bureaucrat extends Card {
  def cost = 4.coin
  def types = Seq(Action)
}

case object Feast extends Card {
  def cost = 4.coin
  def types = Seq(Action)
}

case object Gardens extends Card {
  def cost = 4.coin
  def types = Seq(Victory)
}

case object Militia extends Card {
  def cost = 4.coin
  def types = Seq(Action, Attack)
}

case object Moneylender extends Card {
  def cost = 4.coin
  def types = Seq(Action)
}

case object Remodel extends Card {
  def cost = 4.coin
  def types = Seq(Action)
}

case object Smithy extends Card {
  def cost = 4.coin
  def types = Seq(Action)
  // +3 Cards
}

case object Spy extends Card {
  def cost = 4.coin
  def types = Seq(Action, Attack)
}

case object Thief extends Card {
  def cost = 4.coin
  def types = Seq(Action, Attack)
}

case object ThroneRoom extends Card {
  def cost = 4.coin
  def types = Seq(Action)
}

case object CouncilRoom extends Card {
  def cost = 5.coin
  def types = Seq(Action)
}

case object Festival extends Card {
  def cost = 5.coin
  def types = Seq(Action)
  // +2 Actions +1 Buy +2 Coin
}

case object Laboratory extends Card {
  def cost = 5.coin
  def types = Seq(Action)
  // +2 Cards +1 Actions
}

case object Library extends Card {
  def cost = 5.coin
  def types = Seq(Action)
}

case object Market extends Card {
  def cost = 5.coin
  def types = Seq(Action)
  // +1 Card +1 Action +1 Buy +1 Coin
}

case object Mine extends Card {
  def cost = 5.coin
  def types = Seq(Action)
}

case object Witch extends Card {
  def cost = 5.coin
  def types = Seq(Action, Attack)
}

case object Adventurer extends Card {
  def cost = 6.coin
  def types = Seq(Action)
}
