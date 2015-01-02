package cqr

import cqr.Dir._


object Main {
  def showHelp() = print (
    "CQR v1.0\n" +
    "Usage: scala cqr.Main [-h]\n"
  )

  def main(args: Array[String]) {
    if (args.contains("-h") || args.contains("--help")) showHelp()
    else new CQRRunner().run
  }
}

