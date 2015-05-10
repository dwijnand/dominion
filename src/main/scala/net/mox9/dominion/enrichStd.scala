package net.mox9.dominion

case class AnyW[A](private val x: A) extends AnyVal {
  def maybe[B](pf: A ?=> B): Option[B] = pf lift x

  @inline def sideEffect(body: Unit): A = x
  @inline def doto(f: A => Unit): A     = sideEffect(f(x))
  @inline def pipe[B](f: A => B): B     = f(x)
  @inline def |>[B](f: A => B): B       = f(x)
}
