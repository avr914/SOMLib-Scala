package com.somlib.som

import com.somlib.converters.StandardConverters._
import breeze.linalg.Vector
import com.somlib.som.Exceptions.UndefinedPropertyException

/**
  * Created by arvind on 9/4/16.
  */
class SOMTrainer {
  var nNodes: Int = None[Int]
  protected var model: SOMModel = None[SOMModel]
  protected var data: Array[Vector[Double]] = None[Array[Vector[Double]]]
  protected var sampleFunction: (Iterable[Vector[Double]], Int) => Array[Vector[Double]] = None[Function]
  protected var useData: Boolean = Properties("useData")

  def init(_data: Iterable[Vector[Double]], sampleFunction: (Iterable[Vector[Double]], Int) => Array[Vector[Double]],
           nRows: Int, nCols: Int): Unit = {
    nNodes = nRows * nCols
    val neuronData = sampleFunction(_data,nNodes)
    initializeModelWithData(neuronData, nRows, nCols)
    useData = false
  }

  def init(_data: Iterable[Vector[Double]], nRows: Int, nCols: Int): Unit = {
    nNodes = nRows * nCols
    data = _data.toArray
    val neuronData = Utils.takeSample(data,nNodes)
    initializeModelWithData(neuronData, nRows, nCols)
  }

  def initializeModelWithData(neuronData: Array[Vector[Double]], nRows: Int, nCols: Int): SOMModel = {
    val neurons: Array[Neuron] = Array.tabulate(nNodes)(i => new Neuron(i,i/nCols,i%nCols,neuronData(i)))
    model = new SOMModel(neurons, nRows, nCols)
  }

  def setProperty(key: String, value: String): Unit = {
    if(Properties.isValidProperty(key)) Properties.setProperty(key, value)
    else throw new UndefinedPropertyException
  }

  // store source data and sample function
  // store source data as array
  // allow forceable array conversion
}
