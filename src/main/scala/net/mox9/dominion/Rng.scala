package net.mox9.dominion

case class Rng(seed: Long) extends AnyVal {
  def nextInt: Int -> Rng = {
    val newSeed = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFFL
    (newSeed >>> 16).toInt -> Rng(newSeed)
  }
}
