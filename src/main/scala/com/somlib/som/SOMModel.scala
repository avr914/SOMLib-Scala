package com.somlib.som

import java.util.Properties

import breeze.linalg.Vector
import com.esotericsoftware.kryo.io.{Input, Output}
import com.esotericsoftware.kryo.{Kryo, KryoSerializable}
import com.somlib.som.Exceptions.{DisallowedAccessException, UndefinedPropertyException}

/**
  * Created by arvind on 9/2/16.
  */
class SOMModel(neurons: Array[Neuron], nRows: Int, nCols: Int) extends Serializable {

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

  def setProperty(key: String, value: String): Unit = {
    if(!key.contains("model")) throw new DisallowedAccessException("Only model properties can be modified from here")
    Properties.setProperty(key, value)
  }

  val cachedProperties: Properties = Properties.properties
}

object SOMModel {
  def autodecayRadius(startRadius: Double, timeConst: Double, currentIter:Int, nIter:Int): Double = {
    startRadius * Math.exp(-currentIter/timeConst)
  }

  def autodecayLearningRate(startLR: Double, currentIter:Int, nIter: Int): Double = {
    startLR * Math.exp(-2*currentIter.toDouble/nIter)
  }
}