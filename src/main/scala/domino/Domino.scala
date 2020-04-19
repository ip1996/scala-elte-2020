package domino

import scala.collection.mutable

case class Domino(left : Int, right : Int)

class DominoChain {
  //represents and undirected graph
  private var chain = new mutable.HashMap[Int, List[Int]]

  //where the chain will start
  private var start : Domino = null

  //Filling the DominoChain like an undirected graph
  def AddEdge (domino : Domino): Unit = {
    if (chain contains domino.left) {
      chain(domino.left) = domino.right :: chain(domino.left)
    } else {
      chain += (domino.left -> List(domino.right))
    }
    if (chain contains domino.right) {
      chain(domino.right) = domino.left :: chain(domino.right)
    } else {
      chain += (domino.right -> List(domino.left))
    }
    start = domino
  }

  //The chain is connected if the represented graph is strongly connected
  //Depth First Search to check every node is available
  def IsConnected() : Boolean = {
    var visitionTable = new mutable.HashMap[Int, Boolean]
    chain.keys.foreach(k => visitionTable += (k -> false))
    def DFS(node : Int, visitionTable : mutable.HashMap[Int, Boolean]): Unit ={
      visitionTable(node) = true
      chain(node).foreach(n => {
        if (!visitionTable(n)) {
          DFS(n, visitionTable)
        }
      })
    }

    DFS(chain.keys.head, visitionTable)
    visitionTable.values.forall(p => p)
  }

  //A domino chain can be created if there is an Euler circuit in the represented undirected graph
  //, so if it's connected and every vertex is even
  def HasChain(): Boolean = {
    IsConnected() && chain.keys.forall(k => chain(k).size % 2 == 0)
  }

  //Print prints out the graph in a pretty simple format
  def Print(): Unit ={
    chain.keys.foreach(k => {
      println(k + ": " + chain(k))
    })
  }

  //RemoveEdge removes edge from the represented graph
  //, in practise it change -1 the deleted adjacent node regarding to both direction
  def RemoveEdge(domino : Domino): Unit ={
    chain(domino.left) = chain(domino.left).updated(chain(domino.left).indexOf(domino.right), -1)
    chain(domino.right) = chain(domino.right).updated(chain(domino.right).indexOf(domino.left), -1)
  }

  //Checks if deleting an edge would cause problem or not in the chain building
  def isValidNextEdge(domino: Domino): Boolean = {
    //Count the reachable vertices in the represented graph from a start vertex
    def DFSCount(node : Int, visitCountTable : mutable.HashMap[Int, Boolean]): Int = {
      visitCountTable(node) = true
      var count = 1
      chain(node).foreach(n => {
        if (n != -1 && !visitCountTable(n)) {
          count += DFSCount(n, visitCountTable)
        }
      })
      count
    }


    if (chain(domino.left).count(n => n != -1) == 0) {
      //it means that no other path left from the vertex
      //, so there isn't a valid edge
      false
    } else if (chain(domino.left).count(n => n != -1) == 1) {
      //only one possible edge remained, so it must be valid
      RemoveEdge(domino)
      true
    } else {
      var visitCountTable = new mutable.HashMap[Int, Boolean]
      chain.keys.foreach(k => visitCountTable += (k -> false))
      //counts of the reachable vertexes before deleting the edge
      val beforeCount = DFSCount(domino.left, visitCountTable)
      //removing the edge to check whether it was a bridge or not
      RemoveEdge(domino)
      chain.keys.foreach(k => visitCountTable(k) = false)
      //counts of the reachable vertexes before deleting the edge
      val afterCount = DFSCount(domino.left, visitCountTable)
      if (afterCount < beforeCount) {
        //it means the edge was a bridge in the graph so we can't delete it
        //adding back the edge
        AddEdge(domino)
      }
      //if the two was equal it means the edge was not a bridge, so without it the reachable vertex count remains the same
      //we can delete the node
      afterCount == beforeCount
    }
  }

  //If there is a domino chain then builds the chain represented by a string
  def GetChain(): String = {
    def createChain(node: Int): String = {
      var domChain = ""
      chain(node).foreach(n => {
        if (n != -1 && isValidNextEdge(Domino(node, n))) {
          domChain = " -> " + node + "|" + n + createChain(n)
        }
      })
      domChain
    }

    if (start != null && HasChain()) {
      var visitCountTable = new mutable.HashMap[Int, Boolean]
      chain.keys.foreach(k => visitCountTable += (k -> false))
      RemoveEdge(start)
      var domChain = start.left + "|" + start.right + createChain(start.right)
      chain.clear()
      domChain
    } else {
      chain.clear()
      "No chain found"
    }
  }
}



object Domino extends App {
  val dominoChain = new DominoChain
  var dominos = List(Domino(4,3), Domino(5,1), Domino(3,4), Domino(2,1), Domino(1,3), Domino(5,3), Domino(1,2))
  dominos.foreach(d => dominoChain.AddEdge(d))
  dominoChain.Print()
  println("Connected: " + dominoChain.IsConnected())
  println("Has chain: " + dominoChain.HasChain())
  println(dominoChain.GetChain())
  println("-------------------------")

  dominos = List(Domino(4,1), Domino(1,2), Domino(2,3))
  dominos.foreach(d => dominoChain.AddEdge(d))
  dominoChain.Print()
  println("Connected: " + dominoChain.IsConnected())
  println("Has chain: " + dominoChain.HasChain())
  println(dominoChain.GetChain())
  println("-------------------------")

  dominos = List(Domino(1,2), Domino(2,3), Domino(3,1))
  dominos.foreach(d => dominoChain.AddEdge(d))
  dominoChain.Print()
  println("Connected: " + dominoChain.IsConnected())
  println("Has chain: " + dominoChain.HasChain())
  println(dominoChain.GetChain())
  println("-------------------------")
}
