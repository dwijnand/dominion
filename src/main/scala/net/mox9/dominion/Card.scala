package net.mox9.dominion

trait Card {
  def cost: Coin
  def types: Seq[CardType]
}
