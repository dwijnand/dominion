package net.mox9.dominion

case class VPoint(value: Int) extends AnyVal {
  def +(p: VPoint) = VPoint(value + p.value)
  def -(p: VPoint) = VPoint(value - p.value)
}
