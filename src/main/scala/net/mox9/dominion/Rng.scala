package net.mox9.dominion

case class Rng(seed: Long) extends AnyVal {
  def nextBits(bits: Int): Int -> Rng = {
    val newSeed = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFFL
    (newSeed >>> (48 - bits)).toInt -> Rng(newSeed)
  }

  def nextInt: Int -> Rng = nextBits(32)

  def nextInt(bound: Int): Int -> Rng = {
    val n -> rng = nextBits(31)
    val m = bound - 1
    if ((bound & m) == 0)  // i.e., bound is a power of 2
      ((bound * n.toLong) >> 31).toInt -> rng
    else {
      @tailrec def go(s: Int -> Rng): Int -> Rng = {
        val n -> rng = s
        val r = n % bound
        if (n - r + m < 0)
          go(rng nextBits 31)
        else r -> rng
      }
      go(n -> rng)
    }
  }
}
