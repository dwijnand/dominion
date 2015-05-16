package net.mox9.dominion

sealed abstract class S00BaseKingdomCard extends KingdomCard { def series = S00Base }

object S00Base extends Series {
  val kingdomCards = Seq[S00BaseKingdomCard](
    Cellar, Chapel, Moat,
    Chancellor, Village, Woodcutter,
    Workshop, Bureaucrat, Feast, Gardens, Militia, Moneylender, Remodel, Smithy, Spy, Thief, ThroneRoom,
    CouncilRoom, Festival, Laboratory, Library, Market, Mine, Witch,
    Adventurer
  )
}

case object Cellar extends S00BaseKingdomCard {
  def cost = 2.coins
  def types = Seq(Action)
}

case object Chapel extends S00BaseKingdomCard {
  def cost = 2.coins
  def types = Seq(Action)
}

case object Moat extends S00BaseKingdomCard {
  def cost = 2.coins
  def types = Seq(Action, Reaction)
}

case object Chancellor extends S00BaseKingdomCard {
  def cost = 3.coins
  def types = Seq(Action)
}

case object Village extends S00BaseKingdomCard {
  def cost = 3.coins
  def types = Seq(Action)
  // +1 Card +2 Action
}

case object Woodcutter extends S00BaseKingdomCard {
  def cost = 3.coins
  def types = Seq(Action)
  // +1 Buy +2 Coin
}

case object Workshop extends S00BaseKingdomCard {
  def cost = 3.coins
  def types = Seq(Action)
}

case object Bureaucrat extends S00BaseKingdomCard {
  def cost = 4.coins
  def types = Seq(Action)
}

case object Feast extends S00BaseKingdomCard {
  def cost = 4.coins
  def types = Seq(Action)
}

case object Gardens extends S00BaseKingdomCard {
  def cost = 4.coins
  def types = Seq(Victory)
}

case object Militia extends S00BaseKingdomCard {
  def cost = 4.coins
  def types = Seq(Action, Attack)
}

case object Moneylender extends S00BaseKingdomCard {
  def cost = 4.coins
  def types = Seq(Action)
}

case object Remodel extends S00BaseKingdomCard {
  def cost = 4.coins
  def types = Seq(Action)
}

case object Smithy extends S00BaseKingdomCard {
  def cost = 4.coins
  def types = Seq(Action)
  // +3 Card
}

case object Spy extends S00BaseKingdomCard {
  def cost = 4.coins
  def types = Seq(Action, Attack)
}

case object Thief extends S00BaseKingdomCard {
  def cost = 4.coins
  def types = Seq(Action, Attack)
}

case object ThroneRoom extends S00BaseKingdomCard {
  def cost = 4.coins
  def types = Seq(Action)
}

case object CouncilRoom extends S00BaseKingdomCard {
  def cost = 5.coins
  def types = Seq(Action)
}

case object Festival extends S00BaseKingdomCard {
  def cost = 5.coins
  def types = Seq(Action)
  // +2 Actions +1 Buy +2 Coin
}

case object Laboratory extends S00BaseKingdomCard {
  def cost = 5.coins
  def types = Seq(Action)
  // +2 Card +1 Action
}

case object Library extends S00BaseKingdomCard {
  def cost = 5.coins
  def types = Seq(Action)
}

case object Market extends S00BaseKingdomCard {
  def cost = 5.coins
  def types = Seq(Action)
  // +1 Card +1 Action +1 Buy +1 Coin
}

case object Mine extends S00BaseKingdomCard {
  def cost = 5.coins
  def types = Seq(Action)
}

case object Witch extends S00BaseKingdomCard {
  def cost = 5.coins
  def types = Seq(Action, Attack)
}

case object Adventurer extends S00BaseKingdomCard {
  def cost = 6.coins
  def types = Seq(Action)
}
