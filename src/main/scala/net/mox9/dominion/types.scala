package net.mox9.dominion

case class Actions(value: Int) extends AnyVal {
  def +(a: Actions) = Actions(value + a.value)
}
case class Buys(value: Int) extends AnyVal {
  def +(b: Buys) = Buys(value + b.value)
}
case class Coins(value: Int) extends AnyVal {
  def +(c: Coins) = Coins(value + c.value)
}
case class VPoints(value: Int) extends AnyVal {
  def +(p: VPoints) = VPoints(value + p.value)
}
