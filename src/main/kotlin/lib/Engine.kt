package lib

import java.time.Duration

class Engine<U : User>(
  val width: Double,
  val height: Double,
  val user: U,
  val field: Field,
  val renderer: Renderer,
  val maxTicks: Int,
) {

  var timedOut = false
    private set

  var lost = false
    private set

  val finished
    get() = lost || timedOut || field.asteroids.isEmpty()

  fun fullRun() {
    var step = 0
    while (!finished && step++ < maxTicks) {
      step()
    }
    timedOut = true
  }

  fun step() {
    user.processInputs()
    if (!finished) field.update(width, height, user)
    if (field.contact()) lost = true
    renderer.draw()
  }
}