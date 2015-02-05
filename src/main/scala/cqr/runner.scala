package cqr

import scala.tools.jline.console.ConsoleReader

import cqr._
import cqr.Dir._

class CQRRunner(val qr: QRCode) {
  def this(version: Int) = this(QRCode.fromVersion(version))
  def this() = this(3)
  def this(str: String)  = this(QRCode.fromString(str))

  val frame = new QRFrame(qr)
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

  def run(): String = {
    frame.redraw()

    try while (true) step()
    catch {
      case e: RuntimeException =>
    }

    frame.eraseScreen()
    frame.qr.toString(black = 'X', white = '_', unknown = '?')
  }
}
