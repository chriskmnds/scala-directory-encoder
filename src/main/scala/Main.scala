/**
 * Created by Christos Koumenides on 03/09/2014.
 */

import java.io.{FileWriter, BufferedWriter, File}

object Main {

  val path1 = "/foo/bar/directory"
  val path2 = "/foo/bar/encoded"
  val baseFolder = "PREFIX"
  val directory = new File(path1)

  var linuxCommands = ""
  var tsvFolders = ""
  var tsvFiles = ""

  def main(args: Array[String]): Unit = {

    linuxCommands += "#!/bin/sh" + "\n"
    linuxCommands += "mkdir " + path2 + ";\n"
    linuxCommands += "mkdir " + path2 + "/" + baseFolder + ";\n"

    recurse(directory.listFiles.toList)

    var w = new BufferedWriter(new FileWriter("/foo/bar/commands.sh"))
    w.write(linuxCommands)
    w.close

    w = new BufferedWriter(new FileWriter("/foo/bar/folder-maps.tsv"))
    w.write(tsvFolders)
    w.close

    w = new BufferedWriter(new FileWriter("/foo/bar/file-maps.tsv"))
    w.write(tsvFiles)
    w.close

  }

  def recurse (folder: List[File], base: String = baseFolder) {

    var counter = 0

    folder.foreach(f => {

      f match {

        case x if x.isDirectory => {

          counter += 1

          val basebuf = base + "." + "%04d".format(counter)

          processFolder(x, basebuf, counter)
          processFiles(x, basebuf)
          recurse(x.listFiles.toList, basebuf)
        }
        case _ =>
      }
    })
  }

  def processFolder(folder: File, basebuf: String, counter: Int) {

    val y = basebuf.split("\\.").map(f => f + "/").mkString

    linuxCommands += "mkdir " + path2 + "/" + y + ";\n"
    tsvFolders += folder.toString + "\t" + folder.getName + "\t" + "%04d".format(counter) + "\t" + y + "\n"
  }

  def processFiles(folder: File, basebuf: String) {

    var fileCounter = 0

    folder.listFiles.foreach(f => {

      f match {

        case x if x.isFile && x.getName != ".DS_Store" => {

          fileCounter += 1

          val y = basebuf.split("\\.").map(f => f + "/").mkString

          val fileName = basebuf + "." + "%04d".format(fileCounter)

          linuxCommands += "echo " + x.toString.toCharArray.map(x => if (x.toString != "/") "\\" + x else x).mkString + ";\n"
          linuxCommands += "cp " + x.toString.toCharArray.map(x => if (x.toString != "/") "\\" + x else x).mkString + " " + path2 + "/" + y + fileName  + "." + x.toString.substring(x.toString.lastIndexOf(".") + 1)  + ";\n"
          tsvFiles += x.toString + "\t" + x.getName + "\t" + fileName + "\t" + y + fileName + "\n"
        }
        case _ =>
      }
    })
  }

}
