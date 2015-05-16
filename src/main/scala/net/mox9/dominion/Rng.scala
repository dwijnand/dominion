package net.mox9.dominion

import scala.language.higherKinds

import scala.collection.mutable.ArrayBuffer

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

  def shuffle[T, CC[X] <: TraversableOnce[X]](xs: CC[T])(implicit bf: CBF[CC[T], T, CC[T]]): CC[T] -> Rng = {
    val buf = new ArrayBuffer[T] ++= xs

    def swap(i1: Int, i2: Int): Unit = {
      val tmp = buf(i1)
      buf(i1) = buf(i2)
      buf(i2) = tmp
    }

    val rng = (buf.length to 2 by -1).foldLeft(this) { (rng, n) =>
      val k -> rng2 = rng nextInt n
      swap(n - 1, k)
      rng2
    }

    (bf(xs) ++= buf).result() -> rng
  }
}
object Rng {
  private val seedUniquifier = new AtomicLong(8682522807148012L)

  @tailrec private def newSeedUniquifier(): Long = {
    val current = seedUniquifier.get()
    val next = current * 181783497276652981L
    if (seedUniquifier.compareAndSet(current, next))
      next
    else newSeedUniquifier()
  }

  def randomSeed() = newSeedUniquifier() ^ System.nanoTime()
}
