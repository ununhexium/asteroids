package bin

import lib.Field
import lib.User
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.math.asDegrees
import org.openrndr.writer

class Engine(val width: Double, val height: Double, val user: User, val field: Field) {

  private var _finished = false

  val finished
    get() = _finished

  fun step() {
    user.processInputs()

    if (!finished) {
      field.updateSpaceship(width, height, user)
      field.updateAsteroids()
    }

    if (field.asteroids.isEmpty()) _finished = true
  }

  fun draw(program: Program) {
    program.writer {
      newLine()
      text("Here is a line of text..")
      newLine()
      text(field.asteroids.firstOrNull()?.position?.toString() ?: "No asteroid")
      newLine()
      text(field.asteroids.size.toString())
    }

    program.drawAsteroids()
    program.drawSight(width, height)
    program.drawContactPoint()
    program.drawSpaceship(user)

    if (_finished) {
      if (field.asteroids.isEmpty())
        program.writer {
          program.drawer.text("WIN", width / 2.0, height / 2.0)
        }
    }
  }


  private fun Program.drawAsteroids() {
    drawer.fill = ColorRGBa.GRAY
    drawer.circles(
      field.asteroids.map {
        it.shape(
          width.toDouble(),
          height.toDouble(),
        )
      }
    )

    drawer.stroke = ColorRGBa.WHITE
    drawer.fill = ColorRGBa.TRANSPARENT
    field.spaceship.asteroidInSight?.let {
      drawer.circle(it.shape(width.toDouble(), height.toDouble()))
    }
  }

  private fun Program.drawSight(width: Double, height: Double) {
    val shoot = field.spaceship.shotAsteroid != null
    val target = field.spaceship.inSight != null
    drawer.stroke =
      when {
        shoot -> ColorRGBa.RED
        target -> ColorRGBa.YELLOW.mix(ColorRGBa.RED, field.spaceship.gun.heatRatio)
        else -> ColorRGBa.GREEN.mix(ColorRGBa.BLUE, field.spaceship.gun.heatRatio)
      }


    drawer.lineSegment(field.spaceship.sightShape(width, height))
  }

  private fun Program.drawContactPoint() {
    field.spaceship.inSight?.let {
      drawer.pushTransforms()
      drawer.stroke = ColorRGBa.YELLOW
      drawer.translate(it)
      drawer.shape(contactPointShape)
      drawer.popTransforms()
    }

  }

  private fun Program.drawSpaceship(user: User) {
    drawer.stroke = ColorRGBa.TRANSPARENT

    if (user.up) drawer.fill = ColorRGBa.GREEN
    else drawer.fill = ColorRGBa.YELLOW

    drawer.translate(width * 0.5, height * 0.5)
    drawer.rotate(field.spaceship.angleRad.asDegrees)
    drawer.shape(field.spaceship.shape.shape)
  }

}