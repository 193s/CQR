package cqr

import scala.io.AnsiColor._
import cqr._


class QRFrame(version: Int) {
  val size = QRCode.getSize(version)
  var caret = new ExtPos(Pos(8, 0), 0 to size - 1, 0 to size - 1)
  val pos = Pos(0, 2)
  val qr = new QRCode(version)
  val out = System.err

  private val ESC = 27.toChar

  def eraseScreen() = out.print(s"$ESC[2J$ESC[0;0H")

  def redraw() {
    // erase screen
    eraseScreen()
    out.println(s"$ESC[${pos.y};${pos.x}H")
    // draw qr
    qr.printAll(out)
    // draw caret
    val cx = pos.x + caret.x * 2 + 1
    val cy = pos.y + caret.y + 1
    out.print(s"$ESC[${cy};${cx}H$GREEN_B  $RESET")
    out.print(s"$ESC[${cy};${cx}H")
  }

  def invert() {
    qr.invert(caret.p)
    redraw()
  }

  def move(dir: Dir) {
    caret =>> dir
    redraw()
  }
}


