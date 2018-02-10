package com.huginnmuninnresearch.gameoflife3d

//  ╦`╦┬`┬┌─┐┬┌┐┌┌┐┌``┌─┐┌┐┌┌┬┐``╔╦╗┬`┬┌┐┌┬┌┐┌┌┐┌``╦═╗┌─┐┌─┐┌─┐┌─┐┬─┐┌─┐┬`┬
//  ╠═╣│`││`┬│││││││``├─┤│││`││``║║║│`│││││││││││``╠╦╝├┤`└─┐├┤`├─┤├┬┘│``├─┤
//  ╩`╩└─┘└─┘┴┘└┘┘└┘``┴`┴┘└┘─┴┘``╩`╩└─┘┘└┘┴┘└┘┘└┘``╩╚═└─┘└─┘└─┘┴`┴┴└─└─┘┴`┴
//  © 2018 Edmund Bennett, Huginn and Muninn Research Limited (UK)

import com.huginnmuninnresearch.gameoflife3d.GoLState.stateType
import com.typesafe.scalalogging.LazyLogging

object GameMechanics extends LazyLogging {

  private final val underpopulation: Int = 2
  private final val overpopulation: Int = 15
  private final val reproductionThreshold: Int = 3
  private final val reproductionLimit: Int = 3

  def iterateState(gol: GoLState): GoLState = {
    val newState: stateType = Array.ofDim[Boolean](gol.depthSize, gol.horizontalSize, gol.verticalSize)
    for (d <- 0 until gol.depthSize; v <- 0 until gol.verticalSize; h <- 0 until gol.horizontalSize) {
      val coord: Coordinate = new Coordinate(depth=d, vertical=v, horizontal=h)
      val aliveSurrounding: Int = gol.aliveSurrounding(coord)
      if (gol.alive(coord)) {
        logger.debug(s"Coordinate: $coord is alive")
        if (aliveSurrounding < underpopulation || aliveSurrounding > overpopulation) {
          setElement(coord, newState, false)
        } else {
          setElement(coord, newState, true)
        }
      } else {
        logger.debug(s"Coordinate: $coord is dead")
        if (aliveSurrounding > reproductionThreshold && aliveSurrounding < reproductionLimit) {
          setElement(coord, newState, true)
        } else {
          setElement(coord, newState, false)
        }
      }
    }
    new GoLState(newState)
  }

  def setElement(coord: Coordinate, state: stateType, alive: Boolean): Unit = {
    state(coord.depth)(coord.vertical)(coord.horizontal) = alive
  }

}

