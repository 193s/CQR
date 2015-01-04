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

