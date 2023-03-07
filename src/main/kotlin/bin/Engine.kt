package bin

import lib.User
import org.openrndr.writer

class Engine(val width: Double, val height: Double, val user: User) {

  private var _finished = false

  fun loop() {
    while (!finished) {

      processInputs()

      if (!finished) {
        updateSpaceship(width, height, user)
        updateAsteroids()
      }

      draw()

      checkWinLose()
    }
  }

  private fun checkWinLose() {
    TODO("Not yet implemented")
  }

  private fun draw() {
    TODO("Not yet implemented")
  }

  private fun processInputs() {
    TODO("Not yet implemented")
  }
}