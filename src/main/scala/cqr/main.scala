package cqr

import scala.io.Source
import java.io.PrintWriter
import java.io.FileNotFoundException
import java.io.File

import cqr.Dir._


object Main {
  case class Config (
    version: Int = 3,
    out: File = null,
    in: File = null
  )

  val parser = new scopt.OptionParser[Config]("cqr") {
    head("CQR", "1.0")
    opt[File]('i', "in") valueName("<file>") action { (x, c) =>
      c.copy(in = x) } text("input file")
    opt[File]('o', "out") valueName("<file>") action { (x, c) =>
      c.copy(out = x) } text("output file")
    opt[Int]('v', "version") action { (x, c) =>
      c.copy(version = x) } text("specify version (new QR with properly size will be generated)")

    help("help") text("display this message")
  }

  // show err and exit
  def errExit(s: String) {
    println(s)
    sys.exit(-1)
  }

  def main(args: Array[String]) {
    parser.parse(args, Config()) match {
      case Some(config) =>
        (Option(config.in), Option(config.out)) match {
          case (None, Some(outFile)) =>
            try {
              val out = new PrintWriter(outFile)
              val result = new CQRRunner().run()
              println(result)
              out.println(result)
              out.close()
            }
            catch {
              case e: FileNotFoundException =>
                errExit(s"Unable to create '$outFile': Permission denied")
            }

          case (Some(inFile), None) =>
            val text = Source.fromFile(inFile).getLines mkString "\n"
            val result = new CQRRunner(text).run()
            println(result)

          case (Some(inFile), Some(outFile)) =>
            try {
              val text = Source.fromFile(inFile).getLines mkString "\n"
              val out = new PrintWriter(outFile)
              val result = new CQRRunner(text).run()
              println(result)
              out.println(result)
              out.close()
            }
            catch {
              case e: FileNotFoundException =>
                errExit(s"Unable to create '$outFile': Permission denied")
            }

          case (None, None) =>
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

