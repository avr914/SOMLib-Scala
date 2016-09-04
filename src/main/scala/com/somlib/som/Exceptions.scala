package com.somlib.som

/**
  * Created by arvind on 9/4/16.
  */
object Exceptions {
  class SOMException(msg: String = "SOM Exception") extends Exception("SOM Exception: " + msg) {}
  class UndefinedPropertyException(msg: String = "Undefined Property Exception") extends SOMException(msg) {}
}
