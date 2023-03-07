package lib

import bin.*
import org.openrndr.math.Vector2
import org.openrndr.shape.LineSegment
import org.openrndr.shape.Triangle
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

data class Spaceship(
  var speed: Vector2,
  var angleRad: Double,
  val maxSpeed: Double = 10.0,
  val acceleration: Double = 10.0 / FPS,
  val rotationSpeed: Double = 2.0 / FPS,
  val sightLength: Double = 300.0,
  var inSight: Vector2? = null,
  var asteroidInSight: Asteroid? = null,
  var shotAsteroid: Asteroid? = null,
  val gun: Gun = Gun(2.0, 1.0, 1.0),
) {
  val shape = Triangle(
    Vector2(
      SHIP_SIZE * cos(START_ANGLE),
      SHIP_SIZE * sin(START_ANGLE)
    ),
    Vector2(
      SHIP_SIZE * cos(START_ANGLE + 1.333 * PI),
      SHIP_SIZE * sin(START_ANGLE + 1.333 * PI)
    ),
    Vector2(
      SHIP_SIZE * cos(START_ANGLE + -1.333 * PI),
      SHIP_SIZE * sin(START_ANGLE + -1.333 * PI)
    ),
  )

  fun updateAzimut(left: Boolean, right: Boolean) {
    if (left) angleRad -= rotationSpeed
    if (right) angleRad += rotationSpeed
  }

  fun updateSpeed(accelerate: Boolean) {
    if (accelerate) {
      val accelerationVector = Vector2(cos(angleRad), sin(angleRad))
      val dSpeed = accelerationVector * acceleration
      val newSpeed = speed + dSpeed
      val realSpeed = newSpeed.length

      val speedLimitationFactor =
        if (realSpeed < maxSpeed) 1.0 else maxSpeed / realSpeed

      if (speed.length < 1.0) speed = Vector2.ONE
      speed = newSpeed * speedLimitationFactor
    }
  }

  fun fullSight(width: Double, height: Double): LineSegment {
    return LineSegment(
      width * 0.5,
      height * 0.5,
      width * 0.5 + cos(angleRad) * sightLength,
      height * 0.5 + sin(angleRad) * sightLength
    )
  }

  fun sightShape(width: Double, height: Double): LineSegment {
    val sl = inSight?.let {
      (it - Vector2(width * 0.5, height * 0.5)).length
    } ?: sightLength

    return LineSegment(
      width * 0.5,
      height * 0.5,
      width * 0.5 + cos(angleRad) * sl,
      height * 0.5 + sin(angleRad) * sl
    )
  }

  fun updateSight(width: Double, height: Double, asteroids: List<Asteroid>) {
    val target = asteroids
      .flatMap { asteroid ->
        fullSight(width, height)
          .intersections(asteroid.shape(width, height)).map { it to asteroid }
      }
      .minByOrNull {
        it.first.distanceTo(Vector2(width / 2.0, height / 2.0))
      }

    inSight = target?.first
    asteroidInSight = target?.second
  }
}
