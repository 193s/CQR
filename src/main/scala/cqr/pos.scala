package cqr

import cqr.Dir._

class ExtPos(private var pos: Pos, xrange: Range, yrange: Range) {
  def x = pos.x
  def y = pos.y

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
  def move(dir: Dir): Pos = dir match {
    case LEFT  => Pos(x - 1, y)
    case RIGHT => Pos(x + 1, y)
    case UP    => Pos(x, y - 1)
    case DOWN  => Pos(x, y + 1)
  }
}

