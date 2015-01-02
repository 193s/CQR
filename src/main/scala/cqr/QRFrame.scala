package cqr

import scala.io.AnsiColor._
import cqr._


class QRFrame(size: Int) {
  var caret = new ExtPos(Pos(0, 0), 0 to size, 0 to size)
  val pos = Pos(0, 2)
  val qr = QRCode(size)
  val out = System.err

  val ESC = 27.toChar

  def eraseScreen() = out.print(s"$ESC[2J$ESC[0;0H")

  def redraw() {
    // erase screen
    eraseScreen()
    out.println(s"$ESC[${pos.y};${pos.x}H")
    // draw qr
    qr.printAll(out)
    // draw caret
    out.print(s"$ESC[${pos.y + caret.y};${pos.x + caret.x*2}H$GREEN_B  $RESET")
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


