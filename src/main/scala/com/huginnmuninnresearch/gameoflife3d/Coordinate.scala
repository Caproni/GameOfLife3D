package com.huginnmuninnresearch.gameoflife3d

//  ╦`╦┬`┬┌─┐┬┌┐┌┌┐┌``┌─┐┌┐┌┌┬┐``╔╦╗┬`┬┌┐┌┬┌┐┌┌┐┌``╦═╗┌─┐┌─┐┌─┐┌─┐┬─┐┌─┐┬`┬
//  ╠═╣│`││`┬│││││││``├─┤│││`││``║║║│`│││││││││││``╠╦╝├┤`└─┐├┤`├─┤├┬┘│``├─┤
//  ╩`╩└─┘└─┘┴┘└┘┘└┘``┴`┴┘└┘─┴┘``╩`╩└─┘┘└┘┴┘└┘┘└┘``╩╚═└─┘└─┘└─┘┴`┴┴└─└─┘┴`┴
//  © 2018 Edmund Bennett, Huginn and Muninn Research Limited (UK)

class Coordinate(val depth: Int, val vertical: Int, val horizontal: Int) {

  override def toString: String = {
    "(" + depth + "," + vertical + "," + horizontal + ")"
  }

  override def hashCode(): Int = super.hashCode()

}

