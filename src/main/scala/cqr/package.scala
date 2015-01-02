package cqr

import cqr.Dir._


object Main {
  def showHelp() = print (
    "CQR v1.0\n" +
    "Usage: scala cqr.Main [-h] [-v <version>]\n"
  )

  def main(args: Array[String]) {
    if (args.contains("-h") || args.contains("--help")) showHelp()
    else {
      if (args.contains("-v")) {
        val version = try args(args.indexOf("-v") + 1).toInt catch {
          case e: NullPointerException =>
            showHelp()
            sys.exit(-1)
          case e: NumberFormatException =>
            println("Invalid version format")
            sys.exit(-1)
        }
        if ((1 to 40).contains(version) == false) {
          println("Invalid version: " + version)
          sys.exit(-1)
        }
        new CQRRunner(version).run()
      }
      else new CQRRunner().run()
    }
  }
}

