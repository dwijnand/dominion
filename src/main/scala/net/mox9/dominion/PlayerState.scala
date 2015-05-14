package net.mox9.dominion

trait PlayerView {
  def deck        : Deck
  def handSize    : Int
  def discardPile : DiscardPile

  def actions : Actions
  def buys    : Buys
  def coins   : Coins
}

// TODO: Drop case to stop copy?
// TODO: Split state from turn state?
class PlayerState(
  val deck        : Deck,
  val hand        : Vector[Card],
  val discardPile : DiscardPile,

  val actions : Actions,
  val buys    : Buys,
  val coins   : Coins,

  rng: Rng
) extends PlayerView {

  def handSize = hand.size

  def draw(n: Int): PlayerState =
    (1 to n).foldLeft(this) { (s, _) =>
      s.deck.draw match {
        case Some(card -> remDeck) => s.copy(remDeck, s.hand :+ card)
        case None                  =>
          val newDeck -> newRng = discardPile newDeck rng
          newDeck.draw match {
            case Some(card -> remDeck) => s.copy(remDeck, s.hand :+ card, DiscardPile.empty, rng = newRng)
            case None                  => s.copy(newDeck, s.hand,         DiscardPile.empty, rng = newRng)
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
  ) = new PlayerState(deck, hand, discardPile, actions, buys, coins, rng)
}
object PlayerState {
  def create(rng0: Rng): PlayerState = {
    val startingCards = List(Copper, Copper, Copper, Copper, Copper, Copper, Copper, Estate, Estate, Estate)
    val deck -> rng = Deck.shuffleNew(startingCards, rng0)
    new PlayerState(deck, Vector.empty, DiscardPile.empty, 0.actions, 0.buys, 0.coins, rng)
  }
}

// TODO: Pretty typeclass
// TODO: Spec draw
// drawing 5 makes hand 5

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

// drawing reduces by 1
// what about when empty?

//case class Hand ??

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
// appending adds to topCard
// appending multiple adds to topCard

//trait PosInt
