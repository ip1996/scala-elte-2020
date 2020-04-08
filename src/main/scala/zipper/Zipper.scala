package zipper

import scala.annotation.tailrec

object Zipper extends App {
  sealed trait FileSystem
  case class File(var name : String) extends FileSystem
  case class Directory(var name : String, val parent : Directory, var content : List[FileSystem]) extends FileSystem
  //case object Root extends Directory(name = "/", parent = Root , content = List.empty)

  class Os {
    val root : Directory = Directory("/", root, List.empty)
    var currentNode : Directory = root

    def cd(path : String) : Os = {
      @tailrec
      def findNext(fs : List[FileSystem], dirName : String): Directory = {
          fs match {
            case h::rest => h match {
              case d: Directory if d.name == dirName => d
              case _ => findNext(rest, dirName)
            }
            case Nil => throw new Exception("NotFoundDirectory")
          }
      }
      if (path == "/") {
        currentNode = root
        return this
      }

      if(path.isEmpty) {
        return this
      }

      val isAbsolute = path.head == '/'
      if (isAbsolute) {
        var cr : Directory = root
        path.split("/").drop(1).foreach(dirName => {
          cr = findNext(cr.content, dirName)
        })
        currentNode = cr
      } else {
        var cr : Directory = currentNode
        path.split("/").foreach(dirName => {
          if (dirName == "..") {
            cr = cr.parent
          } else if (dirName == ".") {
            cr = cr
          } else {
            cr = findNext(cr.content, dirName)
          }
        })
        currentNode = cr
      }
      this
    }

    def rm(path : String) : Os = {
      val dirNames = path.split("/")
      val pathToContainingDir = dirNames.dropRight(1).mkString("/")
      val toBeDeleted = dirNames.last

      var cr = currentNode
      cd(pathToContainingDir)
      currentNode.content = currentNode.content.filter{
        case f : File => f.name != toBeDeleted
        case d : Directory => d.name != toBeDeleted
      }

      currentNode = cr
      this
    }

    def mkDir(name : String): Os = {
      val isNameAlreadyReserved = currentNode.content.exists {
                          case f : File => f.name == name
                          case d : Directory => d.name == name
                        }
      if (isNameAlreadyReserved) {
        throw new Exception("NameAlreadyExists")
      }
      currentNode.content = Directory(name, currentNode, List.empty) :: currentNode.content
      this
    }

    def mkFile(name : String) : Os = {
      val isNameAlreadyReserved = currentNode.content.exists {
        case f : File => f.name == name
        case d : Directory => d.name == name
      }
      if (isNameAlreadyReserved) {
        throw new Exception("NameAlreadyExists")
      }
      currentNode.content = File(name) :: currentNode.content
      this
    }

    def ls(): Os = {
      currentNode.content.foreach {
        case f: File => println("F : " + f.name)
        case d: Directory => println("D : " + d.name)
      }
      this
    }

    def print(): Os = {
      def printDirContent(fs : List[FileSystem], tabs : String): Unit = {
        fs.foreach {
          case f : File => println(tabs + "F : " + f.name)
          case d : Directory => {
            println(tabs + "D : " + d.name)
            printDirContent(d.content, tabs + "\t")
          }
        }
      }
      printDirContent(currentNode.content, "")
      this
    }

  }

  val fileSystem : Os = new Os

  fileSystem
    .mkDir("Downloads")
    .mkDir("Pictures")
    .mkDir("Documents")
    .ls()
    .cd("Pictures")
    .mkDir("Album")
    .mkFile("sun.jpg")
    .mkFile("flower.jpg")
    .rm("sun.jpg")
    .ls()
    .cd("/")
    .print()
}
