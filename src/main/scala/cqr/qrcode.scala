package cqr

import cqr._
import java.io.PrintStream
import scala.io.AnsiColor

import cqr.ModuleType._

// Module Type
object ModuleType {
  case object UNKNOWN extends ModuleType
  case object DARK extends ModuleType
  case object LIGHT extends ModuleType
}
sealed abstract class ModuleType


class QRCode private(val size: Int, var array: QRCode.QRData) {
  private def this(size: Int) = this(size, QRCode.newData(size))


  def printAll(out: PrintStream): Unit = out.println(toAnsiString)

  def toAnsiString =
    array.map(_.map(QRCode.convertStr).mkString).mkString("\n")

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
  type QRData = Array[Array[ModuleType]]
  def newData(size: Int): QRData = Array.fill[ModuleType](size, size)(LIGHT)

  val BLOCK = "  "

  def getSizeFromVersion(v: Int) = {
    require (isValidVersion(v))
    17 + 4*v
  }

  def isValidVersion(v: Int) = 1 <= v && v <= 40

  def convertStr(t: ModuleType): String = (t match {
    case UNKNOWN => AnsiColor.CYAN_B
    case DARK    => AnsiColor.BLACK_B
    case LIGHT   => AnsiColor.WHITE_B
  }) + BLOCK + AnsiColor.RESET

  def char2module(c: Char) = c match {
    case 'X' | 'x' | 'O' | 'o' | '#' | '1' => DARK
    case '_' | '-' | ' ' | '0' => LIGHT
    case '?' => UNKNOWN
  }

  def fromString(string: String) = {
    val lines = string.split('\n')
    val size = lines.length
    require(lines forall(_.length == size))

    val data = (for (line <- lines) yield line.map(char2module).toArray).
               toArray.asInstanceOf[QRData]
    new QRCode(size, data)
  }

  def fromVersion(version: Int, init: Boolean = true) = {
    val size  = getSizeFromVersion(version)
    var array = newData(size)
    if (init) {
      val D = DARK
      val L = LIGHT

      val l1 = array(0)
      l1(0) = D
      l1(1) = D
      l1(2) = D
      l1(3) = D
      l1(4) = D
      l1(5) = D
      l1(6) = D

      val l2 = array(1)
      l2(0) = D
      l2(1) = L
      l2(2) = L
      l2(3) = L
      l2(4) = L
      l2(5) = L
      l2(6) = D

      val l3 = array(2)
      l3(0) = D
      l3(1) = L
      l3(2) = D
      l3(3) = D
      l3(4) = D
      l3(5) = L
      l3(6) = D

      val l4 = array(3)
      l4(0) = D
      l4(1) = L
      l4(2) = D
      l4(3) = D
      l4(4) = D
      l4(5) = L
      l4(6) = D

      val l5 = array(4)
      l5(0) = D
      l5(1) = L
      l5(2) = D
      l5(3) = D
      l5(4) = D
      l5(5) = L
      l5(6) = D

      val l6 = array(5)
      l6(0) = D
      l6(1) = L
      l6(2) = L
      l6(3) = L
      l6(4) = L
      l6(5) = L
      l6(6) = D

      val l7 = array(6)
      l7(0) = D
      l7(1) = D
      l7(2) = D
      l7(3) = D
      l7(4) = D
      l7(5) = D
      l7(6) = D
    }
    new QRCode(size, array)
  }
}


