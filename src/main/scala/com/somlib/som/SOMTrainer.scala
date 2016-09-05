package com.somlib.som

import java.io.{File, FileInputStream, FileOutputStream, IOException}

import com.somlib.converters.StandardConverters._
import breeze.linalg.Vector
import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.{Input, Output}
import com.somlib.som.Exceptions.DisallowedAccessException

import scala.reflect.io.Path

/**
  * Created by arvind on 9/4/16.
  */
class SOMTrainer {
  var nNodes: Int = None[Int]
  protected var model: SOMModel = None[SOMModel]
  protected var data: Array[Vector[Double]] = None[Array[Vector[Double]]]
  protected var sampleFunction: (Int) => Array[Vector[Double]] = None[(Int) => Array[Vector[Double]]]
  protected var useData: Boolean = Properties("useData")

  def init(_data: Iterable[Vector[Double]], sampleFunction: (Iterable[Vector[Double]], Int) => Array[Vector[Double]],
           nRows: Int, nCols: Int, resetProperties: Boolean = false): Unit = {
    nNodes = nRows * nCols
    val neuronData = sampleFunction(nNodes)
    initializeModelWithData(neuronData, nRows, nCols)
    useData = false
  }

  def init(_data: Iterable[Vector[Double]], nRows: Int, nCols: Int, resetProperties: Boolean = false): Unit = {
    nNodes = nRows * nCols
    data = _data.toArray
    val neuronData = Utils.takeSample(data,nNodes)
    initializeModelWithData(neuronData, nRows, nCols)
  }

  def initializeModelWithData(neuronData: Array[Vector[Double]], nRows: Int, nCols: Int): SOMModel = {
    val neurons: Array[Neuron] = Array.tabulate(nNodes)(i => new Neuron(i,i/nCols,i%nCols,neuronData(i)))
    model = new SOMModel(neurons, nRows, nCols)
    model
  }

  def setProperty(key: String, value: String): Unit = {
    if(key.contains("model")) throw new DisallowedAccessException("Model properties can only be modified from the " +
      "model file")
    Properties.setProperty(key, value)
  }

  def read(filePath: String, loadProperties: Boolean = false): SOMModel = {
    val kryo: Kryo = new Kryo()
    val path = new Path(filePath)
    if(path.exists) throw new IOException("The specified file does not exist")

    // must be exactly the same as save's registration
    kryo.register(classOf[Vector[Double]], 0)
    kryo.register(classOf[Neuron], 1)
    kryo.register(classOf[java.util.Properties], 2)
    kryo.register(classOf[SOMModel], 3)
    val input = new Input(new FileInputStream(path.toString))
    model = kryo.readObject[SOMModel](input, classOf[SOMModel])
    nNodes = model.numCols * model.numRows
    if(loadProperties) Properties.loadModelProperties(model.cachedProperties)
    input.close
    return model
  }

  def write(filePath: String, mkdirs: Boolean = false): Unit = {
    val kryo: Kryo = new Kryo()
    val file = new File(filePath)
    if(mkdirs) file.mkdirs

    // must be exactly the same as load's registration
    kryo.register(classOf[Vector[Double]], 0)
    kryo.register(classOf[Neuron], 1)
    kryo.register(classOf[java.util.Properties], 2)
    kryo.register(classOf[SOMModel], 3)
    val output: Output = new Output(new FileOutputStream(filePath))
    kryo.writeObject(output, model)
  }

  def iteration(T: Int, radius: Double, LR: Double): Unit = {
    val sample = if(useData) Utils.singleSample(data) else sampleFunction(1)(0)
    val BMU: Neuron = model.findBMU(sample)
    for(i <- 0 until nNodes) {
      if(BMU.eucDist(model(i)) < radius*radius) {
        val influence = BMU.influence(model(i),radius)
        val const = influence*LR
        model(i).update(sample, const)
      }
    }
  }

  def train(totalIter: Int, suppressOutput: Boolean = false): SOMModel = {
    val nRows = model.numRows
    val nCols = model.numCols
    val nNodes = nRows * nCols
    val startRadius = Math.ceil(Math.max(nRows, nCols).toDouble / 2)
    val startLR = 0.8
    val timeConst = totalIter / Math.log(startRadius)
    for(i <- 1 to totalIter) {
      val radius = SOMModel.autodecayRadius(startRadius,timeConst,i,totalIter)
      val LR = SOMModel.autodecayLearningRate(startLR,i,totalIter)
      iteration(i, radius, LR)
      if(!suppressOutput) println("Epoch: %d, Learning Rate: %f, Radius: %f".format(i,LR,radius))
      else print("\rProgress: %.1f%%".format((100 * i.toDouble / totalIter)))
    }
    println("\rProgress: 100%")
    model
  }

}
