package com.somlib.converters

import breeze.linalg.{DenseVector, SparseVector, Vector}
import com.somlib.som.Properties
import com.somlib.converters.StandardConverters._
import org.apache.commons.math3.linear.RealVector


/**
  * Created by arvind on 9/4/16.
  */
object VectorConverters {
  implicit def arrayToBreeze(array: Array[Double]): Vector[Double] = {
    if(Properties.getProperty("useSparse")) SparseVector[Double](array)
    else DenseVector[Double](array)
  }
  implicit def apacheRealVectorToBreeze(rv: RealVector[Double]): Vector[Double] = {
    if(Properties.getProperty("useSparse")) SparseVector[Double](rv.toArray)
    else DenseVector[Double](rv.toArray)
  }
}
