package com.somlib.som

import java.util.Properties
import com.somlib.som.Exceptions.UndefinedPropertyException

/**
  * Created by arvind on 9/4/16.
  */
object Properties {
  private[som] val properties: java.util.Properties = new java.util.Properties
  def validProperties = properties.keySet()
  def isValidProperty(key: String): Boolean = properties.containsKey(key)
  private[som] def setProperty(key: String, value: String): Unit = {
    if(isValidProperty(key)) properties.setProperty(key, value)
    else throw new UndefinedPropertyException("%s is not a defined property".format(key))
  }
  def getProperty(key: String): String = properties.getProperty(key)
  def apply(key: String): String = getProperty(key)
  private[som] def resetProperties: Unit = {
    setProperty("model.useSparseVector","false")
    setProperty("trainer.useData","true")
  }
  private[som] def loadModelProperties(modelProperties: java.util.Properties): Unit = {
    properties.setProperty("model.useSparseVector",modelProperties.getProperty("model.useSparseVector"))
  }
  setProperty("model.useSparseVector","false")
  setProperty("trainer.useData","true")

}
