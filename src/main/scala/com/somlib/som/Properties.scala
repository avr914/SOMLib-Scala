package com.somlib.som

import java.util.Properties

import scala.collection.mutable.HashMap;

/**
  * Created by arvind on 9/4/16.
  */
object Properties {
  val properties: Properties = new Properties
  def validProperties = properties.keySet()
  def isValidProperty(key: String): Boolean = validProperties.contains(key)
  def setProperty(key: String, value: String): Unit = properties.setProperty(key, value)
  def getProperty(key: String): String = properties.getProperty(key)
  def apply(key: String): String = getProperty(key)
  setProperty("useSparse","false")
  setProperty("useData","true")
}
