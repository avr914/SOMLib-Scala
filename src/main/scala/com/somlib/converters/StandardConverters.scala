package com.somlib.converters

import com.somlib.som.Utils

/**
  * Created by arvind on 9/4/16.
  */
object StandardConverters {
  implicit def stringToBoolean(str: String): Boolean = str.toBoolean
  implicit def arrayTakeSample[A](array: Array[A], sampleSize: Int, lowerLimit: Int, upperLimit: Int): Array[A] =
    Utils.takeSample[A](array, sampleSize, lowerLimit, upperLimit)
}
