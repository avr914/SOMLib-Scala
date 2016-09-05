package com.somlib.som

import breeze.linalg.Vector

/**
  * Created by arvind on 9/2/16.
  */
class Neuron(_id: Int, _x: Int, _y: Int, _point: Vector[Double]) extends Serializable {
  val id = _id
  val x = _x
  val y = _y
  var point = _point

  def update(inputVec: Vector[Double], const: Double): Unit = {
    point = point + const * (inputVec - point)
  }

  def influence(otherNeuron: Neuron, radius: Double): Double = {
    val dist = this.eucDist(otherNeuron)
    Math.exp(-dist*dist/(2*radius*radius))
  }

  def manDist(otherNeuron: Neuron): Int = {
    Math.abs(x - otherNeuron.x) + Math.abs(y - otherNeuron.y)
  }

  def eucDist(otherNeuron: Neuron): Double = {
    Math.pow(x - otherNeuron.x,2) + Math.pow(y - otherNeuron.y,2)
  }

  def dist(otherPoint: Vector[Double]): Double = {
    val a = point - otherPoint
    a dot a
  }

  def dist(otherNeuron: Neuron): Double = {
    val a = point - otherNeuron.point
    a dot a
  }
}
