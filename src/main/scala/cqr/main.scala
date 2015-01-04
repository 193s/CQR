package cqr

import java.io.PrintWriter
import java.io.File

import cqr.Dir._


object Main {
  case class Config (
    version: Int = 3,
    out: File = new File("-"),
    in: File = new File("-")
  )
  val parser = new scopt.OptionParser[Config]("scopt") {
    head("CQR", "1.0")
    opt[File]('i', "in") valueName("<file>") action { (x, c) =>
      c.copy(in = x) } text("input file")
    opt[File]('o', "out") valueName("<file>") action { (x, c) =>
      c.copy(out = x) } text("output file")
    opt[Int]('v', "version") action { (x, c) =>
      c.copy(version = x) } text("version of QR Code")

    help("help") text("display this message")
  }

  def errExit(s: String) {
    println(s)
    sys.exit(-1)
  }

  def main(args: Array[String]) {
    parser.parse(args, Config()) match {
      case Some(config) =>
      if (config.out.toString != "-") {
        val outFile = config.out
        try {
          val out = new PrintWriter(outFile)
          val result = new CQRRunner().run()
          println(result)
          out.println(result)
          out.close()
        }
        catch {
          case e: java.io.FileNotFoundException =>
            errExit(s"Unable to create '$outFile': Permission denied")
        }
      }
      else {
        val version = config.version
        if (QRCode.isValidVersion(version) == false)
          errExit("Invalid version: " + version)
        println(new CQRRunner(version).run())
      }

      // bad arguments
      case None => sys.exit(-1)
    }
  }
}

