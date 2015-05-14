package net.mox9

import scala.language.implicitConversions

package object dominion {
  type ->[+A, +B]             = Product2[A, B]
  type ?=>[-A, +B]            = PartialFunction[A, B]
  type \/[+A, +B]             = Either[A, B]
  type CBF[-From, -Elem, +To] = scala.collection.generic.CanBuildFrom[From, Elem, To]
  type CTag[T]                = scala.reflect.ClassTag[T]
  type tailrec                = scala.annotation.tailrec

  val -> = Product2

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

  implicit class Product2W[A, B](p: A -> B) extends AnyVal {
    @inline def mapFst[A2](f: A => A2): A2 -> B = f(p._1) -> p._2
    @inline def mapSnd[B2](f: B => B2): A -> B2 = p._1 -> f(p._2)
  }
  @inline def fst[A, B](p: A -> B): A = p._1
  @inline def snd[A, B](p: A -> B): B = p._2

  implicit class AnyWithOpt[T](x: T) {
    @inline def some : Option[T] = Some(x)
    @inline def opt  : Option[T] = Option(x)
  }
  @inline def some[T](x: T) : Option[T] = Some(x)
  @inline def none[T]       : Option[T] = None

  implicit class AnyWithEither[T](x: T) {
    @inline def left[B]:  T \/ B = Left(x)
    @inline def right[A]: A \/ T = Right(x)
  }
  @inline def left[A, B](x: A)  : A \/ B = Left(x)
  @inline def right[A, B](x: B) : A \/ B = Right(x)

  implicit class IntToResources(private val n: Int) extends AnyVal {
    def actions = Actions(n)
    def buys    = Buys(n)
    def coins   = Coins(n)
    def vps     = VPoints(n)
  }
}
