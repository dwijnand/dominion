package net.mox9.dominion

case class VPoints(value: Int) extends AnyVal {
  def +(p: VPoints) = VPoints(value + p.value)
}
