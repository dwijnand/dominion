package net.mox9

import scala.language.implicitConversions

package object dominion {
  type AtomicLong = java.util.concurrent.atomic.AtomicLong

  type ->[+A, +B]  = scala.Product2[A, B]
  type ?=>[-A, +B] = scala.PartialFunction[A, B]
  type CTag[T]     = scala.reflect.ClassTag[T]
  type tailrec     = scala.annotation.tailrec

  type \/[+A, +B]             = scala.util.Either[A, B]
  type CBF[-From, -Elem, +To] = scala.collection.generic.CanBuildFrom[From, Elem, To]

  val -> = Product2

  @inline def classTag[T: CTag]: CTag[T] = implicitly[CTag[T]]

  @inline def nanoTime: Long = java.lang.System.nanoTime

  @inline def lalign(width: Int): String = if (width == 0) "%s" else s"%-${width}s"
  @inline def ralign(width: Int): String = if (width == 0) "%s" else s"%${width}s"

  @inline def const[T, U](x: T)(y: U): T = Function.const(x)(y)
  @inline def breakOut[From, T, To](implicit b: CBF[Nothing, T, To]): CBF[From, T, To] =
    scala.collection.breakOut[From, T, To]

  implicit class AnyW[A](private val x: A) extends AnyVal {
    @inline def maybe[B](pf: A ?=> B): Option[B] = pf lift x

    @inline def sideEffect(body: Unit): A = x
    @inline def doto(f: A => Unit): A     = sideEffect(f(x))
    @inline def pipe[B](f: A => B): B     = f(x)
    @inline def |>[B](f: A => B): B       = f(x)
  }

  implicit class AnyWithPp[A](private val x: A) extends AnyVal {
    def pp() = println(x)
  }

  implicit class Product2W[A, B](private val p: A -> B) extends AnyVal {
    @inline def mapFst[A2](f: A => A2): (A2, B) = { val (a -> b) = p ; f(a) -> b }
    @inline def mapSnd[B2](f: B => B2): (A, B2) = { val (a -> b) = p ; a -> f(b) }
  }
  @inline def fst[A, B](p: A -> B): A = p._1
  @inline def snd[A, B](p: A -> B): B = p._2

  implicit class AnyWithOpt[T](private val x: T) {
    @inline def some : Option[T] = Some(x)
    @inline def opt  : Option[T] = Option(x)
  }
  @inline def some[T](x: T) : Option[T] = Some(x)
  @inline def none[T]       : Option[T] = None

  implicit class AnyWithEither[T](private val x: T) {
    @inline def left[B]:  T \/ B = Left(x)
    @inline def right[A]: A \/ T = Right(x)
  }
  @inline def left[A, B](x: A)  : A \/ B = Left(x)
  @inline def right[A, B](x: B) : A \/ B = Right(x)

  @inline def nil[A]  : List[A]   = Nil
  @inline def seqZ[A] : Seq[A]    = Nil
  @inline def vecZ[A] : Vector[A] = Vector.empty
  implicit class AnyWithColl[A](private val x: A) extends AnyVal {
    @inline def vec: Vector[A] = Vector(x)
  }
  // TODO: Define "mapMap" to remove "xs map (_ map ...)"

  implicit class IntToResources(private val n: Int) extends AnyVal {
    def cards   = CardCount(n)
    def actions = Actions(n)
    def buys    = Buys(n)
    def coins   = Coins(n)
    def vps     = VPoints(n)
  }
}
