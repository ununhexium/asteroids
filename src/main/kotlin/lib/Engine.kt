package lib

import java.time.Duration

class Engine<U : User>(
  val width: Double,
  val height: Double,
  val user: U,
  val field: Field,
  val renderer: Renderer,
  val maxTicks: Int = Int.MAX_VALUE,
) {

  var timedOut = false
    private set

  var lost = false
    private set

  val finished
    get() = lost || timedOut || field.asteroids.isEmpty() || step >= maxTicks

  var step = 0
    private set

  fun fullRun() {
    while (!finished) {
      step()
    }
  }

  fun step() {
    step++
    if (lost) throw IllegalStateException()
    user.processInputs()
    if (!finished) field.update(width, height, user)
    if (field.contact()) {
      lost = true
    }
    if (step++ >= maxTicks) timedOut = true
    renderer.draw()
  }
}