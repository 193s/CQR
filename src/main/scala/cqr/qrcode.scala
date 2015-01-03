package cqr

import cqr._
import java.io.PrintStream
import scala.io.AnsiColor

import cqr.ModuleType._

object ModuleType {
  case object UNKNOWN extends ModuleType
  case object DARK extends ModuleType
  case object LIGHT extends ModuleType
}
sealed abstract class ModuleType


class QRCode private(size: Int) {
  def this(version: Int = 3, init: Boolean = true) {
    this(QRCode.getSize(version))
    if (init) {
      val l1 = array(0)
      l1(0) = DARK
      l1(1) = DARK
      l1(2) = DARK
      l1(3) = DARK
      l1(4) = DARK
      l1(5) = DARK
      l1(6) = DARK

      val l2 = array(1)
      l2(0) = DARK
      l2(1) = LIGHT
      l2(2) = LIGHT
      l2(3) = LIGHT
      l2(4) = LIGHT
      l2(5) = LIGHT
      l2(6) = DARK

      val l3 = array(2)
      l3(0) = DARK
      l3(1) = LIGHT
      l3(2) = DARK
      l3(3) = DARK
      l3(4) = DARK
      l3(5) = LIGHT
      l3(6) = DARK

      val l4 = array(3)
      l4(0) = DARK
      l4(1) = LIGHT
      l4(2) = DARK
      l4(3) = DARK
      l4(4) = DARK
      l4(5) = LIGHT
      l4(6) = DARK

      val l5 = array(4)
      l5(0) = DARK
      l5(1) = LIGHT
      l5(2) = DARK
      l5(3) = DARK
      l5(4) = DARK
      l5(5) = LIGHT
      l5(6) = DARK

      val l6 = array(5)
      l6(0) = DARK
      l6(1) = LIGHT
      l6(2) = LIGHT
      l6(3) = LIGHT
      l6(4) = LIGHT
      l6(5) = LIGHT
      l6(6) = DARK

      val l7 = array(6)
      l7(0) = DARK
      l7(1) = DARK
      l7(2) = DARK
      l7(3) = DARK
      l7(4) = DARK
      l7(5) = DARK
      l7(6) = DARK
    }
  }

  var array = Array.fill[ModuleType](size, size)(LIGHT)

  def printAll(out: PrintStream): Unit = out.println (
    array.map(arr => arr.map(QRCode.convertStr).mkString).mkString("\n")
  )

  def toString(black: Char = 'X', white: Char = '_', unknown: Char = '?') =
    array.map(_.map( _ match {
        case DARK => black
        case LIGHT => white
        case UNKNOWN => unknown
    }).mkString).mkString("\n")

  def setVal(p: Pos): Unit = setVal(p.x, p.y)
  def setVal(x: Int, y: Int) = (value: ModuleType) => array(y)(x) = value

  def invert(p: Pos): Unit = invert(p.x, p.y)
  def invert(x: Int, y: Int) {
    array(y)(x) = (array(y)(x) match {
      case UNKNOWN => LIGHT
      case LIGHT   => DARK
      case DARK   => UNKNOWN
    })
  }

  private def getLine(i: Int) = array(i)
}

object QRCode {
  val BLOCK = "  "

  def getSize(v: Int) = {
    require (v >= 1, v <= 40)
    17 + 4*v
  }

  def convertStr(t: ModuleType): String = (t match {
    case UNKNOWN => AnsiColor.CYAN_B
    case DARK   => AnsiColor.BLACK_B
    case LIGHT   => AnsiColor.WHITE_B
  }) + BLOCK + AnsiColor.RESET
}


