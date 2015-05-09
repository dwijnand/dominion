package net.mox9.dominion

sealed trait Cost extends Any {
  def value: Int
  def +(c: Cost): Cost
  def -(c: Cost): Cost
}
object Cost extends (Int => Cost) {
  def apply(n: Int): Cost = CostImpl(n max 0)
  private final case class CostImpl(value: Int) extends Cost {
    def +(c: Cost): Cost = Cost(value + c.value)
    def -(c: Cost): Cost = Cost(value - c.value)
  }
}
case class IntWithCost(private val n: Int) extends AnyVal {
  def cost = Cost(n)
}

trait Card {
  def cost: Cost
}
