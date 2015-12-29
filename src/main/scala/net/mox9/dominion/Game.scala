package net.mox9.dominion

object Game {
  def main(args: Array[String]): Unit = {
    val seed = args.headOption.fold(Rng.randomSeed() doto (x => println(s"Using seed: $x")))(_.toLong)
    val rng0 = Rng(seed)
    val playerCount -> rng1 = rng0 choose Vector(_2P, _3P, _4P)
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

    sealed trait TextAlign extends Any   { def alignBy(width: Int): String }
    case object LAlign extends TextAlign { def alignBy(width: Int) = width.lalign }
    case object RAlign extends TextAlign { def alignBy(width: Int) = width.ralign }

    // Split name from properties, so auto-lifting is only by type name, not by method
    // Then auto-lift this trait to give it methods, works because implicit conversions don't chain
    sealed trait StrWithAlign extends Any
    case class StrWithAlignOps(str: String, align: TextAlign, slots: Int) extends StrWithAlign {
      def +(s: String) = StrWithAlignOps(str + s, align, slots)
      def slots(n: Int) = StrWithAlignOps(str, align, n)
    }
    object StrWithAlign {
      implicit def liftAny[A](x: A): StrWithAlign = x.lj
      implicit def liftOps(swa: StrWithAlign): StrWithAlignOps = swa match { case swao: StrWithAlignOps => swao }
    }

    implicit class AnyWithTextAlign[A](private val x: A) /*extends AnyVal*/ {
      def lj = StrWithAlignOps(x.toString, LAlign, 1)
      def rj = StrWithAlignOps(x.toString, RAlign, 1)
    }

    implicit class CardPileToStr(cp: CardPile[Card]) {
      @inline def toStr = Vector(cp.count.rj, cp.card.lj)
    }
    implicit class TrashToStr(t: Trash) {
      // TODO: 2 slots / cells / atoms
      @inline def toStr = t.cards.headOption.fold("[No Trash]")(_.toString).lj.slots(2).vec
    }

    val board =
      Vector(
        Vector(provinces.toStr, Vector(card5, card6, card7, card8, card9) flatMap (_.toStr),   golds.toStr).flatten,
        Vector(  duchies.toStr, Vector(card0, card1, card2, card3, card4) flatMap (_.toStr), silvers.toStr).flatten,
        Vector(  estates.toStr, Vector.fill(10)("".lj),                                      coppers.toStr).flatten,
        Vector(   curses.toStr, Vector.fill(10)("".lj),                                        trash.toStr).flatten
      ) filter (_.nonEmpty)

    val cardFormat = "%%%ss %%-%ss"
    val format = s"$cardFormat    $cardFormat  $cardFormat  $cardFormat  $cardFormat  $cardFormat    $cardFormat"

    val colCount = board.maxBy(_.length).length
    val functions = (0 until colCount).toVector map (idx => { case xs: Seq[StrWithAlign] if xs isDefinedAt idx => xs(idx) }: Seq[StrWithAlign] ?=> StrWithAlign)

    // fuck this
    // 0 pad card count

    // TODO: Consider slot > 1, wrt widths below
    val cols = functions map (f => board map (x => f.lift(x).fold("")(_.str)))
    val widths = cols map (_ map (_.length) max)
    val renderLines = board map { row =>
      val (alignsAndSlots -> strings) = row.map(c => (c.align -> c.slots) -> c.str).unzip
      val rowFormat = alignsAndSlots.foldLeft(vecZ[String] -> widths) { case (fmt -> widths) -> (align -> slots) =>
        val (widths1 -> widths2) = widths splitAt slots
        val newFmt1 = align alignBy widths1.sum
        val newFmt = fmt :+ newFmt1
        newFmt -> widths2
      }._1 mkString " "
      s"rowFormat $rowFormat".pp
      rowFormat format (strings: _*)
    }

    val s = renderLines mkString "\n"
    println(s)
    ()
  }

  final case class FunctionGrid[A, B](values: Vector[A], functions: Vector[A => B]) {
    def rows: Vector[Vector[B]]     = values map (v => functions map (f => f(v)))
    def columns: Vector[Vector[B]]  = functions map (f => values map (v => f(v)))
    def renderLines: Vector[String] = {
      val widths = columns map (col => col map (x => x.toString.length) max)
      val rowFormat = widths map lalign mkString " "
      rows map (row => rowFormat.format(row.seq: _*))
    }
    def render: String = renderLines mkString "\n"
  }

  implicit class SeqW[T](private val xs: Seq[T]) extends AnyVal {
    def get(idx: Int): Option[T] = if (xs.isDefinedAt(idx)) xs(idx).some else None
    def getOrElse[U >: T](idx: Int, ifEmpty: => U): U = if (xs.isDefinedAt(idx)) xs(idx) else ifEmpty
  }
}
