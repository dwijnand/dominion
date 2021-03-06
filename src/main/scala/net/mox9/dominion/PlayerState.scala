package net.mox9.dominion

// TODO: Random kingdom cards
// TODO: TrashPile - add to supply - ::[Card]
// TODO: endTurn game over or next player
// TODO: Tabular output
// TODO: Pretty typeclass
// TODO: draw spec
// TODO: drawing 5 makes hand 5
// TODO: drawing reduces by 1
// TODO: drawing when empty?
// TODO: class Hand ??
// TODO: appending adds to topCard
// TODO: appending multiple adds to topCard
// TODO: PosInt/PosZInt type

class CardPile[+T <: Card](val card: T, val count: CardCount) {
  // TODO: Kill CardPile.toString?
  override val toString = s"$count $card"
//def inc = new CardPile[T](card, count + 1.cards)
}

case class Trash(cards: List[Card]) extends AnyVal

class Supply private (
  val coppers : CardPile[Copper.type],   val silvers : CardPile[Silver.type],   val golds     : CardPile[Gold.type],
  val estates : CardPile[Estate.type],   val duchies : CardPile[Duchy.type],    val provinces : CardPile[Province.type],

  val curses: CardPile[Curse.type],   val trash: Trash,

  val card0: CardPile[KingdomCard], val card1: CardPile[KingdomCard], val card2: CardPile[KingdomCard],
  val card3: CardPile[KingdomCard], val card4: CardPile[KingdomCard], val card5: CardPile[KingdomCard],
  val card6: CardPile[KingdomCard], val card7: CardPile[KingdomCard], val card8: CardPile[KingdomCard],
  val card9: CardPile[KingdomCard]
)
object Supply {
  def create(
    playerCount: PlayerCount,
    card0: KingdomCard, card1: KingdomCard, card2: KingdomCard, card3: KingdomCard, card4: KingdomCard,
    card5: KingdomCard, card6: KingdomCard, card7: KingdomCard, card8: KingdomCard, card9: KingdomCard
  ) = {
    // TODO: Additional cards from other series
    // TODO: Province start: 5p -> 15, 6p -> 18
    /*
    2 players:  08 Estate  08 Duchy  08 Province
    3 players:  12 Estate  12 Duchy  12 Province
    4 players:  12 Estate  12 Duchy  12 Province
    5 players:  12 Estate  12 Duchy  15 Province
    6 players:  12 Estate  12 Duchy  18 Province
     */

    val vcCount = if (playerCount.value == 2) 8 else 12

    implicit class IntToPile(x: Int) {
      @inline def *[C <: Card](c: C) = new CardPile[C](c, x.cards)
    }

    def newPile(kc: KingdomCard) = (if (kc.types.contains(Victory)) vcCount else 10) * kc

    new Supply (
      coppers = 60 * Copper,        silvers = 40 * Silver,       golds = 30 * Gold,
      estates = vcCount * Estate,   duchies = vcCount * Duchy,   provinces = vcCount * Province,

      curses = ((playerCount.value - 1) * 10) * Curse,   trash = Trash(Nil),

      card0 = newPile(card0), card1 = newPile(card1), card2 = newPile(card2), card3 = newPile(card3),
      card4 = newPile(card4), card5 = newPile(card5), card6 = newPile(card6), card7 = newPile(card7),
      card8 = newPile(card8), card9 = newPile(card9)
    )
  }
}

class Deck private (cards: List[Card]) {
  def size: Int        = cards.size
  def isEmpty: Boolean = cards.isEmpty

  def draw: Option[Card -> Deck] =
    cards match {
      case Nil    => None
      case h :: t => h -> new Deck(t) some
    }

  // TODO reveal
}
object Deck {
  def shuffleNew(cards: List[Card], rng: Rng): Deck -> Rng = {
    val shuffledCards -> rng2 = rng shuffle cards
    new Deck(shuffledCards) -> rng2
  }
}

class DiscardPile private (cards: List[Card]) {
  def topCard: Option[Card] = cards.headOption
  def isEmpty: Boolean      = cards.isEmpty

  def ::(c: Card): DiscardPile        = new DiscardPile(c :: cards)
  def :::(cs: Seq[Card]): DiscardPile = new DiscardPile(cs.toList ::: cards)

  def newDeck(rng: Rng): Deck -> Rng = Deck.shuffleNew(cards, rng)
}
object DiscardPile {
  def empty: DiscardPile = new DiscardPile(Nil)
}

class PlayerState private (
  val deck        : Deck,
  val hand        : Vector[Card],
  val discardPile : DiscardPile
)
object PlayerState {
  def create(rng0: Rng): PlayerState -> Rng = {
    val startingCards = List(Copper, Copper, Copper, Copper, Copper, Copper, Copper, Estate, Estate, Estate)
    val deck = Deck.shuffleNew(startingCards, rng0)
    deck mapFst (deck => new PlayerState(deck, Vector.empty, DiscardPile.empty))
  }
}

trait CurrentPlayerView {
  def deck        : Deck
  def handSize    : Int
  def discardPile : DiscardPile

  def actions : Actions
  def buys    : Buys
  def coins   : Coins
}

class CurrentPlayerState(
          val deck        : Deck,
  private val hand        : Vector[Card],
          val discardPile : DiscardPile,

  val actions : Actions,
  val buys    : Buys,
  val coins   : Coins,

  rng: Rng
) extends CurrentPlayerView {

  def handSize: Int = hand.size

  def draw(n: Int): CurrentPlayerState =
    (1 to n).foldLeft(this) { (cs, _) =>
      cs.deck.draw match {
        case Some(card -> remDeck) => cs.copy(remDeck, cs.hand :+ card)
        case None                  =>
          val newDeck -> newRng = discardPile newDeck rng
          newDeck.draw match {
            case Some(card -> remDeck) => cs.copy(remDeck, cs.hand :+ card, DiscardPile.empty, rng = newRng)
            case None                  => cs.copy(newDeck, cs.hand,         DiscardPile.empty, rng = newRng)
          }
      }
    }

  private def copy(
    deck        : Deck         = deck,
    hand        : Vector[Card] = hand,
    discardPile : DiscardPile  = discardPile,

    actions : Actions = actions,
    buys    : Buys    = buys,
    coins   : Coins   = coins,

    rng: Rng = rng
  ) = new CurrentPlayerState(deck, hand, discardPile, actions, buys, coins, rng)
}
