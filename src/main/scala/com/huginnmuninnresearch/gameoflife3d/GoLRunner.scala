package com.huginnmuninnresearch.gameoflife3d

//  ╦`╦┬`┬┌─┐┬┌┐┌┌┐┌``┌─┐┌┐┌┌┬┐``╔╦╗┬`┬┌┐┌┬┌┐┌┌┐┌``╦═╗┌─┐┌─┐┌─┐┌─┐┬─┐┌─┐┬`┬
//  ╠═╣│`││`┬│││││││``├─┤│││`││``║║║│`│││││││││││``╠╦╝├┤`└─┐├┤`├─┤├┬┘│``├─┤
//  ╩`╩└─┘└─┘┴┘└┘┘└┘``┴`┴┘└┘─┴┘``╩`╩└─┘┘└┘┴┘└┘┘└┘``╩╚═└─┘└─┘└─┘┴`┴┴└─└─┘┴`┴
//  © 2018 Edmund Bennett, Huginn and Muninn Research Limited (UK)

import com.huginnmuninnresearch.gameoflife3d.GoLState.stateType
import com.typesafe.scalalogging.LazyLogging

class GoLRunner(val depthSize: Int, val verticalSize: Int, val horizontalSize: Int, val initialState: stateType, val iterations: Int) extends LazyLogging {

  assert(initialState.nonEmpty, "State information is empty (depth array)")
  assert(initialState.head.nonEmpty, "State information is empty (horizontal array)")
  assert(initialState.head.head.nonEmpty, "State information is empty (vertical array)")
  assert(initialState.lengthCompare(depthSize) == 0, "Depth size of state does not match state array size")
  assert(initialState.head.lengthCompare(horizontalSize) == 0, "Horizontal size of state does not match state array size")
  assert(initialState.head.head.lengthCompare(verticalSize) == 0, "Vertical size of state does not match state array size")

  val initialGol: GoLState = new GoLState(initialState)

  logger.info("Dimensions (d, v, h) are: " + (depthSize, verticalSize, horizontalSize) + " and number of iterations is: " + iterations)
  logger.info("Starting state is: ")
  logger.info("\n" + initialGol.toString)
  logger.info("Total number alive: " + initialGol.aliveTotal + " of " + verticalSize*horizontalSize*depthSize)
  var oldGol: GoLState = initialGol
  var i: Int = 0
  while (i < iterations && oldGol.aliveTotal != 0) {
    i += 1
    val newGol: GoLState = GameMechanics.iterateState(oldGol)
    logger.info("After iteration: " + i + ". Total number alive is " + newGol.aliveTotal + ". State is: ")
    logger.info("\n" + newGol.toString)
    oldGol = newGol
  }

  if (i < iterations) logger.info("A total of " + (iterations - i) + " iterations were skipped due to population termination")
}

object GoLRunner extends LazyLogging {

  def main(args: Array[String]): Unit = {
    
    logger.info("""
                      GameOfLife3D: A simple 3D version of Conway's Game of Life, implemented in Scala 
                      Copyright (C) 2018 Edmund Bennett
                  
                      This program is free software: you can redistribute it and/or modify
                      it under the terms of the GNU General Public License as published by
                      the Free Software Foundation, either version 3 of the License, or
                      (at your option) any later version.
                  
                      This program is distributed in the hope that it will be useful,
                      but WITHOUT ANY WARRANTY; without even the implied warranty of
                      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
                      GNU General Public License for more details.
                  
                      You should have received a copy of the GNU General Public License
                      along with this program.  If not, see <http://www.gnu.org/licenses/>.""")

    val initialState: stateType = Array(Array(Array(false, false, false, false, false, false),
                                        Array(false, false, false, true, true, true),
                                        Array(false, false, false, false, false, false),
                                        Array(false, true, false, false, false, false),
                                        Array(false, true, false, false, false, false),
                                        Array(false, true, false, false, false, false)),
                                      Array(Array(false, false, false, false, false, false),
                                        Array(false, false, false, true, true, true),
                                        Array(false, false, false, false, false, false),
                                        Array(false, true, false, false, false, false),
                                        Array(false, true, false, false, false, false),
                                        Array(false, true, false, false, false, false)))

    new GoLRunner(2, 6, 6, initialState, 30)

  }

}
