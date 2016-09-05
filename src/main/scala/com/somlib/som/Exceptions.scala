package com.somlib.som

/**
  * Created by arvind on 9/4/16.
  */
object Exceptions {
  class SOMException(msg: String) extends Exception("SOM Exception: " + msg) {}
  class UndefinedPropertyException(msg: String) extends SOMException("Undefined Property Exception: " + msg) {}
  class DisallowedAccessException(msg: String) extends SOMException("Disallowed Access Exception: " + msg) {}
}
