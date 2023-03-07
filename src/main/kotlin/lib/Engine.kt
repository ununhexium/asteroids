package lib

class Engine(
  val width: Double,
  val height: Double,
  val user: User,
  val field: Field,
  val renderer: Renderer,
  private val name: String =
    "E${(index++).toString().padStart(2, '0')}",
) {
  companion object {
    private var index = 0
  }

  var timeout = false
    private set

  val finished
    get() = timeout || field.asteroids.isEmpty()

  fun runUpTo(steps: Int) {
    var step = 0
    while (!finished && step++ < steps) {
      step()
    }
    timeout = true
  }

  fun step() {
    user.processInputs()
    if (!finished) field.update(width, height, user)
    renderer.draw()
  }
}