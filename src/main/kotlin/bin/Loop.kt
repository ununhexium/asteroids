import bin.*
import lib.HumanUser
import lib.User
import org.openrndr.Program
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.math.asDegrees
import org.openrndr.writer

fun main() = application {

  val user = HumanUser()
  val engine = Engine(WIDTH.toDouble(), HEIGHT.toDouble(), user)

  configure {
    width = WIDTH
    height = HEIGHT
    windowResizable = false
  }

  program {

    extend {

      writer {
        newLine()
        text("Here is a line of text..")
        newLine()
        text(asteroids.firstOrNull()?.position?.toString() ?: "No asteroid")
        newLine()
        text(asteroids.size.toString())
      }

      val w = width.toDouble()
      val h = height.toDouble()

      user.decideInputs(this)

      if (!finished) {
        updateSpaceship(w, h, user)
        updateAsteroids()
      }

      drawAsteroids()
      drawSight(w, h)
      drawContactPoint()
      drawSpaceship(user)

      checkWinLose()

      if (finished) {
        if (asteroids.isEmpty())
          writer {
            drawer.text("WIN", width / 2.0, height / 2.0)
          }
      }
    }
  }
}

fun checkWinLose() {
  if (asteroids.isEmpty()) finished = true
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
