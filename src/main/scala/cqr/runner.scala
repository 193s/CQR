package cqr

import scala.tools.jline.console.ConsoleReader

import cqr._
import cqr.Dir._

class CQRRunner(version: Int) {
  def this() = this(3)

  def fromVersion(v: Int) = {
    require (v >= 1, v <= 40)
    17 + 4*v
  }

  val size = fromVersion(version)
  val frame = new QRFrame(size)
  val reader = new ConsoleReader()

  private def step() {
    reader.readVirtualKey().toChar match {
      case 'h' => frame.move(LEFT)
      case 'j' => frame.move(DOWN)
      case 'k' => frame.move(UP)
      case 'l' => frame.move(RIGHT)

      case 'x' | ' ' => frame.invert()
      case 'q' => throw new RuntimeException()

      case _ =>
    }
  }

  def run() {
    frame.redraw()

    try while (true) step()
    catch {
      case e: RuntimeException =>
    }

    frame.eraseScreen()
    System.out.println(frame.qr.toString(black='X', white='_', unknown='?'))
  }
}
