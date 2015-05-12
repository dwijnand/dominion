package net.mox9.dominion

sealed trait Phase
case object ActionPhase  extends Phase
case object BuyPhase     extends Phase
case object CleanupPhase extends Phase

object Phase {
  val order = Seq(ActionPhase, BuyPhase, CleanupPhase)
}
