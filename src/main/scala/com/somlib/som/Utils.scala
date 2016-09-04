package com.somlib.som

import scala.collection.immutable.NumericRange
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.HashMap
import scala.util.Random

/**
  * Created by arvind on 9/4/16.
  */
object Utils {
  /*
  def sample(n, lower, upper):
    result = []
    pool = {}
    for _ in xrange(n):
        i = random.randint(lower, upper)
        x = pool.get(i, i)
        pool[i] = pool.get(lower, lower)
        lower += 1
        result.append(x)
    return result
    */
  def takeSample[A](array: Array[A], sampleSize: Int, lowerLimit: Int, upperLimit: Int): Array[A] = {
    var indices: ArrayBuffer[Int] = ArrayBuffer.empty[Int]
    var pool: HashMap[Int, Int] = HashMap.empty[Int, Int]
    val size: Int = array.length
    val range = Range.Int(0,sampleSize,1)
    val rand = new Random(System.currentTimeMillis)
    var lower = lowerLimit
    val upper = upperLimit
    for(_ <- range) {
      val i = rand.nextInt(upper - lower) + lower
      val x = pool.getOrElse(i,i)
      pool += i -> pool.getOrElse(lower,lower)
      lower += 1
      indices += x
    }
    indices.toArray.map(index => array(index))
  }

  def takeSample[A](array: Array[A], sampleSize: Int): Array[A] = {
    takeSample(array, sampleSize, 0, array.length)
  }

}
