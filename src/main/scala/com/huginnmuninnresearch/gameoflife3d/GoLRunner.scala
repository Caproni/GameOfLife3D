package com.huginnmuninnresearch.gameoflife3d

//  ╦`╦┬`┬┌─┐┬┌┐┌┌┐┌``┌─┐┌┐┌┌┬┐``╔╦╗┬`┬┌┐┌┬┌┐┌┌┐┌``╦═╗┌─┐┌─┐┌─┐┌─┐┬─┐┌─┐┬`┬
//  ╠═╣│`││`┬│││││││``├─┤│││`││``║║║│`│││││││││││``╠╦╝├┤`└─┐├┤`├─┤├┬┘│``├─┤
//  ╩`╩└─┘└─┘┴┘└┘┘└┘``┴`┴┘└┘─┴┘``╩`╩└─┘┘└┘┴┘└┘┘└┘``╩╚═└─┘└─┘└─┘┴`┴┴└─└─┘┴`┴
//  © 2018 Edmund Bennett, Huginn and Muninn Research Limited (UK)

import com.huginnmuninnresearch.gameoflife3d.GoLState.stateType

class GoLRunner(val depthSize: Int, val verticalSize: Int, val horizontalSize: Int, val initialState: stateType, val iterations: Int) {

  assert(initialState.nonEmpty, "State information is empty (depth array)")
  assert(initialState.head.nonEmpty, "State information is empty (horizontal array)")
  assert(initialState.head.head.nonEmpty, "State information is empty (vertical array)")
  assert(initialState.lengthCompare(depthSize) == 0, "Depth size of state does not match state array size")
  assert(initialState.head.lengthCompare(horizontalSize) == 0, "Horizontal size of state does not match state array size")
  assert(initialState.head.head.lengthCompare(verticalSize) == 0, "Vertical size of state does not match state array size")

  val initialGol: GoLState = new GoLState(initialState)

  println("Dimensions (d, v, h) are: " + (depthSize, verticalSize, horizontalSize) + " and number of iterations is: " + iterations)
  println("Starting state is: ")
  println(initialGol)
  println("Total number alive: " + initialGol.aliveTotal + " of " + verticalSize*horizontalSize*depthSize)
  var oldGol: GoLState = initialGol
  var i: Int = 0
  while (i < iterations && oldGol.aliveTotal != 0) {
    i += 1
    val newGol: GoLState = GameMechanics.iterateState(oldGol)
    println("After iteration: " + i + ". Total number alive is " + newGol.aliveTotal + ". State is: ")
    println(newGol)
    oldGol = newGol
  }

  if (i < iterations) println("A total of " + (iterations - i) + " iterations were skipped due to population termination")
}

object GoLRunner {

  def main(args: Array[String]): Unit = {

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
