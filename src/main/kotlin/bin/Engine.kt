package bin

import lib.User
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.math.asDegrees
import org.openrndr.writer

class Engine(val width: Double, val height: Double, val user: User) {

  private var _finished = false

  val finished
    get() = _finished

  fun step() {
    processInputs()

    if (!finished) {
      updateSpaceship(width, height, user)
      updateAsteroids()
    }

    checkWinLose()
  }

  fun draw(program: Program) {
    program.writer {
      newLine()
      text("Here is a line of text..")
      newLine()
      text(asteroids.firstOrNull()?.position?.toString() ?: "No asteroid")
      newLine()
      text(asteroids.size.toString())
    }

    program.drawAsteroids()
    program.drawSight(width, height)
    program.drawContactPoint()
    program.drawSpaceship(user)

    if (_finished) {
      if (asteroids.isEmpty())
        program.writer {
          program.drawer.text("WIN", width / 2.0, height / 2.0)
        }
    }
  }

  private fun checkWinLose() {
    if (asteroids.isEmpty()) _finished = true
  }

  private fun processInputs() {
    user.processInputs()
  }


  private fun Program.drawAsteroids() {
    drawer.fill = ColorRGBa.GRAY
    drawer.circles(
      asteroids.map {
        it.shape(
          width.toDouble(),
          height.toDouble(),
        )
      }
    )

    drawer.stroke = ColorRGBa.WHITE
    drawer.fill = ColorRGBa.TRANSPARENT
    spaceship.asteroidInSight?.let {
      drawer.circle(it.shape(width.toDouble(), height.toDouble()))
    }
  }

  private fun Program.drawSight(width: Double, height: Double) {
    val shoot = spaceship.shotAsteroid != null
    val target = spaceship.inSight != null
    drawer.stroke =
      when {
        shoot -> ColorRGBa.RED
        target -> ColorRGBa.YELLOW.mix(ColorRGBa.RED, spaceship.gun.heatRatio)
        else -> ColorRGBa.GREEN.mix(ColorRGBa.BLUE, spaceship.gun.heatRatio)
      }


    drawer.lineSegment(spaceship.sightShape(width, height))
  }

  private fun Program.drawContactPoint() {
    spaceship.inSight?.let {
      drawer.pushTransforms()
      drawer.stroke = ColorRGBa.YELLOW
      drawer.translate(it)
      drawer.shape(contactPointShape)
      drawer.popTransforms()
    }

  }

  fun Program.drawSpaceship(user: User) {
    drawer.stroke = ColorRGBa.TRANSPARENT

    if (user.up) drawer.fill = ColorRGBa.GREEN
    else drawer.fill = ColorRGBa.YELLOW

    drawer.translate(width * 0.5, height * 0.5)
    drawer.rotate(spaceship.angleRad.asDegrees)
    drawer.shape(spaceship.shape.shape)
  }

}