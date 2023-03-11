package lib

import bin.*
import org.openrndr.extra.noise.random
import org.openrndr.math.Vector2
import org.openrndr.shape.Circle
import java.lang.Math.pow

data class Asteroid(
  private var _position: Vector2,
  val size: Int,
  val speed: Vector2
) {
  companion object {

    val shotsCache = mutableMapOf<Int, MutableMap<Int, Double>>()
    /**
     * Computes the weight of the asteroids in number of shots required to
     * destroy them
     *
     * Asteroid with
     *
     * split = 3
     * hard = 4
     *
     * required shots = 1 + 3 + 9 + 27
     */
    fun shotsToDestroy(asteroid: Asteroid): Double {
      val c = shotsCache.computeIfAbsent(ASTEROID_SPLIT_COUNT) { mutableMapOf() }
      val s = ASTEROID_SPLIT_COUNT.toDouble()
      return (MIN_ASTEROID_SIZE..asteroid.size).sumOf { size ->
        c.computeIfAbsent(size) {
          pow(s, (size - 1).toDouble())
        }
      }
    }
  }

  val displaySize = size * 10.0

  val position
    get() = _position
  
  fun shape(
    maxX: Double,
    maxY: Double,
  ): Circle {
    return Circle(getDisplayPosition(maxX, maxY), displaySize)
  }

  fun getDisplayPosition(maxX: Double, maxY: Double) =
    Vector2(_position.x.mod(maxX), _position.y.mod(maxY))

  fun updatePosition(spaceship: Spaceship) {
    _position = _position + speed - spaceship.speed
  }

  fun split() = if (size <= MIN_ASTEROID_SIZE) listOf()
  else (0..ASTEROID_SPLIT_COUNT).map {
    this.copy(
      _position = position,
      size = size - 1,
      speed = Vector2(
        random(MIN_ASTEROID_SPEED, MAX_ASTEROID_SPEED) - AVERAGE_ASTEROID_SPEED,
        random(MIN_ASTEROID_SPEED, MAX_ASTEROID_SPEED) - AVERAGE_ASTEROID_SPEED
      )
    )
  }

}
