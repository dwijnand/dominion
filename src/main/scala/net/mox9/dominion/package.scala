package net.mox9

import scala.language.implicitConversions

package object dominion {
  type ->[+A, +B]             = Product2[A, B]
  type ?=>[-A, +B]            = PartialFunction[A, B]
  type \/[+A, +B]             = Either[A, B]
  type CBF[-From, -Elem, +To] = scala.collection.generic.CanBuildFrom[From, Elem, To]
  type CTag[T]                = scala.reflect.ClassTag[T]
  type tailrec                = scala.annotation.tailrec

  type Cards = Seq[Card]

  def classTag[T: CTag]: CTag[T] = implicitly[CTag[T]]

  def const[T, U](x: T)(y: U): T = Function.const(x)(y)
  def breakOut[From, T, To](implicit b: CBF[Nothing, T, To]) = scala.collection.breakOut[From, T, To]

  implicit class AnyW[A](private val x: A) extends AnyVal {
    def maybe[B](pf: A ?=> B): Option[B] = pf lift x

    @inline def sideEffect(body: Unit): A = x
    @inline def doto(f: A => Unit): A     = sideEffect(f(x))
    @inline def pipe[B](f: A => B): B     = f(x)
    @inline def |>[B](f: A => B): B       = f(x)
  }

  implicit class IntWithCoins(private val n: Int) extends AnyVal {
    def coins = Coins(n)
  }

  implicit class IntWithVps(private val n: Int) extends AnyVal {
    def vps = VPoints(n)
  }
}
