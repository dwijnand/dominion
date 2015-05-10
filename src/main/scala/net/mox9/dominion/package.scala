package net.mox9

import scala.language.implicitConversions

package object dominion {
  type ->[+A, +B]             = Product2[A, B]
  type ?=>[-A, +B]            = PartialFunction[A, B]
  type \/[+A, +B]             = Either[A, B]
  type CBF[-From, -Elem, +To] = scala.collection.generic.CanBuildFrom[From, Elem, To]
  type CTag[T]                = scala.reflect.ClassTag[T]
  type tailrec                = scala.annotation.tailrec

  def classTag[T: CTag]: CTag[T] = implicitly[CTag[T]]

  def const[T, U](x: T)(y: U): T = Function.const(x)(y)
  def breakOut[From, T, To](implicit b: CBF[Nothing, T, To]) = scala.collection.breakOut[From, T, To]

  implicit def anyW[A](x: A): AnyW[A] = new AnyW(x)

  implicit case class IntWithCoin(private val n: Int) extends AnyVal {
    def coin = Coin(n)
  }

  implicit case class IntWithVp(private val n: Int) extends AnyVal {
    def vp = VPoint(n)
  }
}
