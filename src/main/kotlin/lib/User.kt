package lib

interface User {
  val up: Boolean
  val left: Boolean
  val right: Boolean
  val shoot: Boolean

  fun processInputs()
}