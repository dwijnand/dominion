package net.mox9.dominion

object Game {
  def main(args: Array[String]): Unit = {
    val seed = args.headOption.fold(Rng.randomSeed() doto (x => println(s"Using seed: $x")))(_.toLong)
    val rng0 = Rng(seed)
    val playerCount -> rng1 = rng0.chooseInt(2, 4) mapFst PlayerCount
    println(s"$playerCount players")
    val cards0 -> rng2 = rng1.shuffle(S00Base.kingdomCards) mapFst (_ take 10)
    val cards = cards0.sorted(Ordering[(Int, String)].on[Card](c => (-c.cost.value, c.name)))
    val supply = Supply.create(
      playerCount,
      cards(0), cards(1), cards(2), cards(3), cards(4),
      cards(5), cards(6), cards(7), cards(8), cards(9)
    )
    showSupply(supply)
    ()
  }

  def showSupply(supply: Supply): Unit = {
    import supply._

    // PhilosophersStone is 17 chars

    /*
    Actions: 01
    Buys:    01
    Coins:   01
     */

    // End Turn
    // Player Treasures

    implicit class CardPileToStr(cp: CardPile[Card]) {
      @inline def toStr = Vector(cp.count.toString, cp.card.toString)
    }

    val board =
      Vector(
        Vector(provinces.toStr, Vector(card5, card6, card7, card8, card9) flatMap (_.toStr),   golds.toStr).flatten,
        Vector(  duchies.toStr, Vector(card0, card1, card2, card3, card4) flatMap (_.toStr), silvers.toStr).flatten,
        Vector(  estates.toStr, Vector.fill(10)(""),                                         coppers.toStr).flatten,
        Vector(   curses.toStr, Vector.fill(12)("")                                                       ).flatten
      ) filter (_.nonEmpty)

    val values = board
    val functions = (0 until 14).toVector map (idx => { xs: Seq[String] => xs(idx) })
    val cardFormat = "%%%ss %%-%ss"
    val format = s"$cardFormat    $cardFormat  $cardFormat  $cardFormat  $cardFormat  $cardFormat    $cardFormat"

    def rows = values map (x => functions map (f => f(x)))
    def cols = functions map (f => values map (x => f(x)))
    def renderLines = {
      def widths = cols map (_ map (_.length) max)
      val rowFormat = format format (widths: _*)
      rows map (row => rowFormat format (row: _*))
    }
    val s = renderLines mkString "\n"
    println(s)
    ()
  }

  implicit class SeqW[T](private val xs: Seq[T]) extends AnyVal {
    def get(idx: Int): Option[T] = if (xs.isDefinedAt(idx)) xs(idx).some else None
    def getOrElse[U >: T](idx: Int, ifEmpty: => U): U = if (xs.isDefinedAt(idx)) xs(idx) else ifEmpty
  }
}
