package com.huginnmuninnresearch.gameoflife3d

//  ╦`╦┬`┬┌─┐┬┌┐┌┌┐┌``┌─┐┌┐┌┌┬┐``╔╦╗┬`┬┌┐┌┬┌┐┌┌┐┌``╦═╗┌─┐┌─┐┌─┐┌─┐┬─┐┌─┐┬`┬
//  ╠═╣│`││`┬│││││││``├─┤│││`││``║║║│`│││││││││││``╠╦╝├┤`└─┐├┤`├─┤├┬┘│``├─┤
//  ╩`╩└─┘└─┘┴┘└┘┘└┘``┴`┴┘└┘─┴┘``╩`╩└─┘┘└┘┴┘└┘┘└┘``╩╚═└─┘└─┘└─┘┴`┴┴└─└─┘┴`┴
//  © 2018 Edmund Bennett, Huginn and Muninn Research Limited (UK)

import scala.annotation._
import com.huginnmuninnresearch.gameoflife3d.GoLState.{coordType, stateType}
import com.typesafe.scalalogging.LazyLogging

class GoLState(val state: stateType) extends LazyLogging {

  val horizontalSize: Int = state.head.length
  val verticalSize: Int = state.head.head.length
  val depthSize: Int = state.length

  def findNeighbours(coord: Coordinate): coordType = {

    var neighbours: coordType = List[Coordinate]()

    val hMin = if (coord.horizontal > 0) coord.horizontal-1 else 0
    val hMax = if (coord.horizontal < horizontalSize-1) coord.horizontal+1 else horizontalSize-1

    val vMin = if (coord.vertical > 0) coord.vertical-1 else 0
    val vMax = if (coord.vertical < verticalSize-1) coord.vertical+1 else verticalSize-1

    val dMin = if (coord.depth > 0) coord.depth-1 else 0
    val dMax = if (coord.depth < depthSize-1) coord.depth+1 else depthSize-1

    for (d <- dMin to dMax; v <- vMin to vMax; h <- hMin to hMax) {
      if (v != coord.vertical || h != coord.horizontal || d != coord.depth) {
          neighbours = new Coordinate(depth = d, vertical = v, horizontal = h) :: neighbours
        }
      }
    logger.debug(s"Adjacent coordinates to $coord are: $neighbours")
    neighbours
  }

  def aliveSurrounding(coord: Coordinate): Int = {
    val adjacentCoords: coordType = findNeighbours(coord)
    sumNeighbours(adjacentCoords)
  }

  @tailrec
  private def sumNeighbours(adjacentCoords: coordType, sum: Int = 1): Int = {
    if (adjacentCoords.isEmpty) {
      logger.debug(s"Alive surrounding coordinate: $sum")
      sum
    } else {
      if (alive(adjacentCoords.head)) {
//        logger.debug("Of these " + adjacentCoords.head + " is alive")
        sumNeighbours(adjacentCoords.tail, sum+1)
      } else sumNeighbours(adjacentCoords.tail, sum)
    }
  }

  def aliveTotal: Int = {
    var aliveTotal: Int = 0
    for {d <- 0 until depthSize; v <- 0 until verticalSize; h <- 0 until horizontalSize
         if alive(new Coordinate(depth=d, vertical=v, horizontal=h))} aliveTotal += 1
    aliveTotal
  }

  override def toString: String = {
    val eol: String = System.getProperty("line.separator")
    var output: String = ""
    for (d <- 0 until depthSize; v <- 0 until verticalSize; h <- 0 until horizontalSize) {
      output = if (alive(new Coordinate(depth=d, vertical=v, horizontal=h))) output + GoLState.aliveMarker else output + GoLState.deadMarker
      if (h == horizontalSize-1) output += eol
      if ((v == verticalSize-1) && (h == horizontalSize-1) && (d != depthSize-1)) output += (eol + "=" + eol)
    }
    output
  }

  override def hashCode(): Int = super.hashCode()

  def equals(gol: GoLState): Boolean = {
    for (d <- 0 until depthSize; v <- 0 until verticalSize; h <- 0 until horizontalSize) {
      val coord: Coordinate = new Coordinate(depth=d, vertical=v, horizontal=h)
      if (gol.alive(coord).equals(alive(coord))) return false
    }
    true
  }


  def alive(coord: Coordinate): Boolean = {
    state(coord.depth)(coord.horizontal)(coord.vertical)
  }

}

object GoLState {

  type stateType = Array[Array[Array[Boolean]]]
  type coordType = List[Coordinate]

  val aliveMarker: String = "X"
  val deadMarker: String = "."

}
