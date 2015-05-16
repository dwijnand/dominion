package net.mox9.dominion

// TODO: Random kingdom cards
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
// PosInt type ??

class Supply private (
  val coppers : CardCount,   val silvers  : CardCount,   val golds     : CardCount,
  val estates : CardCount,   val dutchies : CardCount,   val provinces : CardCount,

  val curses: CardCount,

  val card0: KingdomCard -> CardCount, val card1: KingdomCard -> CardCount, val card2: KingdomCard -> CardCount,
  val card3: KingdomCard -> CardCount, val card4: KingdomCard -> CardCount, val card5: KingdomCard -> CardCount,
  val card6: KingdomCard -> CardCount, val card7: KingdomCard -> CardCount, val card8: KingdomCard -> CardCount,
  val card9: KingdomCard -> CardCount
)
object Supply {
  def create(
    playerCount: PlayerCount,
    card0: KingdomCard, card1: KingdomCard, card2: KingdomCard, card3: KingdomCard, card4: KingdomCard,
    card5: KingdomCard, card6: KingdomCard, card7: KingdomCard, card8: KingdomCard, card9: KingdomCard
  ) = {
    // TODO: Additional cards from other series
    // TODO: Province start: 5p -> 15, 6p -> 18

    val vcCount = (if (playerCount.value == 2) 8 else 12).cards

    def count(kc: KingdomCard) = kc -> (if (kc.types.contains(Victory)) vcCount else 10.cards)

    new Supply (
      coppers = 60.cards,   silvers  = 40.cards,   golds     = 30.cards,
      estates = vcCount,    dutchies = vcCount,    provinces = vcCount,

      curses = ((playerCount.value - 1) * 10).cards,

      card0 = count(card0), card1 = count(card1), card2 = count(card2), card3 = count(card3),
      card4 = count(card4), card5 = count(card5), card6 = count(card6), card7 = count(card7),
      card8 = count(card8), card9 = count(card9)
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
