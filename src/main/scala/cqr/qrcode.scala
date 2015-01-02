package cqr

import cqr._
import java.io.PrintStream
import scala.io.AnsiColor

import cqr.ModuleType._

object ModuleType {
  case object UNKNOWN extends ModuleType
  case object BLACK extends ModuleType
  case object WHITE extends ModuleType
}
sealed abstract class ModuleType


class QRCode private(size: Int) {
  var array = Array.fill[ModuleType](size, size)(WHITE)

  def printAll(out: PrintStream): Unit = out.println (
    array.map(arr => arr.map(QRCode.convertStr).mkString).mkString("\n")
  )

  def toString(black: Char = 'X', white: Char = '_', unknown: Char = '?') =
    array.map(_.map( _ match {
        case BLACK => black
        case WHITE => white
        case UNKNOWN => unknown
    }).mkString).mkString("\n")

  def setVal(p: Pos): Unit = setVal(p.x, p.y)
  def setVal(x: Int, y: Int) = (value: ModuleType) => array(y)(x) = value

  def invert(p: Pos): Unit = invert(p.x, p.y)
  def invert(x: Int, y: Int) {
    array(y)(x) = (array(y)(x) match {
      case UNKNOWN => WHITE
      case WHITE   => BLACK
      case BLACK   => UNKNOWN
    })
  }

  private def getLine(i: Int) = array(i)
}

object QRCode {
  val BLOCK = "  "

  def apply(size: Int) = new QRCode(size)

  def convertStr(t: ModuleType): String = (t match {
    case UNKNOWN => AnsiColor.CYAN_B
    case BLACK   => AnsiColor.BLACK_B
    case WHITE   => AnsiColor.WHITE_B
  }) + BLOCK + AnsiColor.RESET
}


