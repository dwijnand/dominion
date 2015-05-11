package net.mox9.dominion

sealed trait CardType
case object Action   extends CardType
case object Attack   extends CardType
case object CurseK   extends CardType
case object Reaction extends CardType
case object Treasure extends CardType
case object Victory  extends CardType
