package cqr


object Dir {
  case object UP extends Dir
  case object DOWN extends Dir
  case object LEFT extends Dir
  case object RIGHT extends Dir

  val values = Array(UP, DOWN, LEFT, RIGHT)
}
sealed abstract class Dir {
  import cqr.Dir._
  def unary_- = this match {
    case UP => DOWN
    case DOWN => UP
    case LEFT => RIGHT
    case RIGHT => LEFT
  }
}
