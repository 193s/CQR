package cqr

import cqr.Dir._

class ExtPos(private var pos: Pos, xrange: Range, yrange: Range) {
  def p = pos
  def x = pos.x
  def y = pos.y

  def =>> (dir: Dir) = move(dir)
  def move(dir: Dir): Boolean = {
    val dest = pos + dir
    if (xrange.contains(dest.x) && xrange.contains(dest.y)) {
      pos = dest
      true
    }
    else false
  }
}

case class Pos(x: Int, y: Int) {
  def +(p: Pos) = Pos(x + p.x, y + p.y)
  def -(p: Pos) = this + (-p)
  def +(dir: Dir) = move(dir)
  def move(dir: Dir): Pos = this + dir.pos
  def unary_+ = Pos(x, y)
  def unary_- = Pos(-x, -y)
}

object Dir {
  case object UP extends Dir(Pos(0, -1))
  case object DOWN extends Dir(Pos(0, 1))
  case object LEFT extends Dir(Pos(-1, 0))
  case object RIGHT extends Dir(Pos(1, 0))

  val values = Array(UP, DOWN, LEFT, RIGHT)
}
sealed abstract class Dir(val pos: Pos) {
  import cqr.Dir._
  def unary_- = this match {
    case UP => DOWN
    case DOWN => UP
    case LEFT => RIGHT
    case RIGHT => LEFT
  }
}
