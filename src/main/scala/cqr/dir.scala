package cqr


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
