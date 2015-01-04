package cqr

import java.io.PrintWriter
import java.io.File

import cqr.Dir._


object Main {
  def errExit(s: String) {
    println(s)
    sys.exit(-1)
  }
  def showHelp() = print (
    "CQR v1.0\n" +
    "Usage: scala cqr.Main [-h] [-v <version>]\n"
  )

  def main(args: Array[String]) {
    case class Config(foo: Int = -1, out: File = new File("."), xyz: Boolean = false,
      libName: String = "", version: Int = 3, help: Boolean = false)

    if (args.contains("-h") || args.contains("--help")) showHelp()
    else {
      if (args.contains("-o")) {
        val outFile = new java.io.File(args(args.indexOf("-o") + 1))
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
      else if (args.contains("-v")) {
        val version = try args(args.indexOf("-v") + 1).toInt catch {
          case e: NullPointerException =>
            showHelp()
            sys.exit(-1)
          case e: NumberFormatException =>
            println("Invalid version format")
            sys.exit(-1)
        }
        if (QRCode.isValidVersion(version) == false) {
          println("Invalid version: " + version)
          sys.exit(-1)
        }
        println(new CQRRunner(version).run())
      }
      else println(new CQRRunner().run())
    }
  }
}

