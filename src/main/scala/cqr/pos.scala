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
  def +(dir: Dir) = move(dir)
  def move(dir: Dir) = dir match {
    case LEFT  => Pos(x - 1, y)
    case RIGHT => Pos(x + 1, y)
    case UP    => Pos(x, y - 1)
    case DOWN  => Pos(x, y + 1)
  }
  def unary_- = Pos(-x, -y)
}

