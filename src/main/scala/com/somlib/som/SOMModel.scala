package com.somlib.som

import breeze.linalg.Vector

/**
  * Created by arvind on 9/2/16.
  */
class SOMModel(neurons: Array[Neuron], nRows: Int, nCols: Int) {

  def size() = neurons.size

  def numRows() = nRows

  def numCols() = nCols

  def findBMU(data: Vector[Double]): Neuron = {
    neurons.minBy(point => point.dist(data))
  }

  def classify(data: Vector[Double]): Int = {
    findBMU(data).id
  }

  def apply(id: Int): Neuron = neurons(id)
}
