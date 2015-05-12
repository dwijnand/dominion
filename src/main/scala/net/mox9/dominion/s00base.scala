package net.mox9.dominion

case object Cellar extends Card {
  def cost = 2.coins
  def types = Seq(Action)
}

case object Chapel extends Card {
  def cost = 2.coins
  def types = Seq(Action)
}

case object Moat extends Card {
  def cost = 2.coins
  def types = Seq(Action, Reaction)
}

case object Chancellor extends Card {
  def cost = 3.coins
  def types = Seq(Action)
}

case object Village extends Card {
  def cost = 3.coins
  def types = Seq(Action)
  // +1 Card +2 Action
}

case object Woodcutter extends Card {
  def cost = 3.coins
  def types = Seq(Action)
  // +1 Buy +2 Coin
}

case object Workshop extends Card {
  def cost = 3.coins
  def types = Seq(Action)
}

case object Bureaucrat extends Card {
  def cost = 4.coins
  def types = Seq(Action)
}

case object Feast extends Card {
  def cost = 4.coins
  def types = Seq(Action)
}

case object Gardens extends Card {
  def cost = 4.coins
  def types = Seq(Victory)
}

case object Militia extends Card {
  def cost = 4.coins
  def types = Seq(Action, Attack)
}

case object Moneylender extends Card {
  def cost = 4.coins
  def types = Seq(Action)
}

case object Remodel extends Card {
  def cost = 4.coins
  def types = Seq(Action)
}

case object Smithy extends Card {
  def cost = 4.coins
  def types = Seq(Action)
  // +3 Cards
}

case object Spy extends Card {
  def cost = 4.coins
  def types = Seq(Action, Attack)
}

case object Thief extends Card {
  def cost = 4.coins
  def types = Seq(Action, Attack)
}

case object ThroneRoom extends Card {
  def cost = 4.coins
  def types = Seq(Action)
}

case object CouncilRoom extends Card {
  def cost = 5.coins
  def types = Seq(Action)
}

case object Festival extends Card {
  def cost = 5.coins
  def types = Seq(Action)
  // +2 Actions +1 Buy +2 Coin
}

case object Laboratory extends Card {
  def cost = 5.coins
  def types = Seq(Action)
  // +2 Cards +1 Actions
}

case object Library extends Card {
  def cost = 5.coins
  def types = Seq(Action)
}

case object Market extends Card {
  def cost = 5.coins
  def types = Seq(Action)
  // +1 Card +1 Action +1 Buy +1 Coin
}

case object Mine extends Card {
  def cost = 5.coins
  def types = Seq(Action)
}

case object Witch extends Card {
  def cost = 5.coins
  def types = Seq(Action, Attack)
}

case object Adventurer extends Card {
  def cost = 6.coins
  def types = Seq(Action)
}
