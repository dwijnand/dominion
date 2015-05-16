package net.mox9.dominion

// TODO: Supply
// TODO: Random kingdom cards
// TODO: Series/Release field on cards and enumerate members
// TODO: endTurn game over or next player
// TODO: Tabular output

class PlayerState(
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
  state: PlayerState,

  val actions : Actions,
  val buys    : Buys,
  val coins   : Coins,

  rng: Rng
) extends CurrentPlayerView {

          def deck        : Deck         = state.deck
  private def hand        : Vector[Card] = state.hand
          def handSize    : Int          = state.hand.size
          def discardPile : DiscardPile  = state.discardPile

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
  ) = new CurrentPlayerState(new PlayerState(deck, hand, discardPile), actions, buys, coins, rng)
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
