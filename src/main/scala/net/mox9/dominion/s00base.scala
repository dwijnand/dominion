package net.mox9.dominion

case object Cellar extends KingdomCard {
  def cost = 2.coins
  def types = Seq(Action)
}

case object Chapel extends KingdomCard {
  def cost = 2.coins
  def types = Seq(Action)
}

case object Moat extends KingdomCard {
  def cost = 2.coins
  def types = Seq(Action, Reaction)
}

case object Chancellor extends KingdomCard {
  def cost = 3.coins
  def types = Seq(Action)
}

case object Village extends KingdomCard {
  def cost = 3.coins
  def types = Seq(Action)
  // +1 Card +2 Action
}

case object Woodcutter extends KingdomCard {
  def cost = 3.coins
  def types = Seq(Action)
  // +1 Buy +2 Coin
}

case object Workshop extends KingdomCard {
  def cost = 3.coins
  def types = Seq(Action)
}

case object Bureaucrat extends KingdomCard {
  def cost = 4.coins
  def types = Seq(Action)
}

case object Feast extends KingdomCard {
  def cost = 4.coins
  def types = Seq(Action)
}

case object Gardens extends KingdomCard {
  def cost = 4.coins
  def types = Seq(Victory)
}

case object Militia extends KingdomCard {
  def cost = 4.coins
  def types = Seq(Action, Attack)
}

case object Moneylender extends KingdomCard {
  def cost = 4.coins
  def types = Seq(Action)
}

case object Remodel extends KingdomCard {
  def cost = 4.coins
  def types = Seq(Action)
}

case object Smithy extends KingdomCard {
  def cost = 4.coins
  def types = Seq(Action)
  // +3 Card
}

case object Spy extends KingdomCard {
  def cost = 4.coins
  def types = Seq(Action, Attack)
}

case object Thief extends KingdomCard {
  def cost = 4.coins
  def types = Seq(Action, Attack)
}

case object ThroneRoom extends KingdomCard {
  def cost = 4.coins
  def types = Seq(Action)
}

case object CouncilRoom extends KingdomCard {
  def cost = 5.coins
  def types = Seq(Action)
}

case object Festival extends KingdomCard {
  def cost = 5.coins
  def types = Seq(Action)
  // +2 Actions +1 Buy +2 Coin
}

case object Laboratory extends KingdomCard {
  def cost = 5.coins
  def types = Seq(Action)
  // +2 Card +1 Action
}

case object Library extends KingdomCard {
  def cost = 5.coins
  def types = Seq(Action)
}

case object Market extends KingdomCard {
  def cost = 5.coins
  def types = Seq(Action)
  // +1 Card +1 Action +1 Buy +1 Coin
}

case object Mine extends KingdomCard {
  def cost = 5.coins
  def types = Seq(Action)
}

case object Witch extends KingdomCard {
  def cost = 5.coins
  def types = Seq(Action, Attack)
}

case object Adventurer extends KingdomCard {
  def cost = 6.coins
  def types = Seq(Action)
}
