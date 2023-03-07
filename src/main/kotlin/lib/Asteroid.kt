package lib

import bin.*
import org.openrndr.extra.noise.random
import org.openrndr.math.Vector2
import org.openrndr.shape.Circle

data class Asteroid(
  private var _position: Vector2,
  val hardness: Int,
  val speed: Vector2
) {
  val position
    get() = _position

  fun shape(
    maxX: Double,
    maxY: Double,
  ): Circle {
    val displayPos = Vector2(_position.x.mod(maxX), _position.y.mod(maxY))
    return Circle(displayPos, hardness * 10.0)
  }

  fun updatePosition(spaceship: Spaceship) {
    _position = _position + speed - spaceship.speed
  }

  fun split() = if (hardness <= MIN_ASTEROID_SIZE) listOf()
  else (0..ASTEROID_SPLIT_COUNT).map{
    this.copy(
      _position = position,
      hardness = hardness - 1,
      speed = Vector2(
        random(MIN_ASTEROID_SPEED, MAX_ASTEROID_SPEED) - AVERAGE_ASTEROID_SPEED,
        random(MIN_ASTEROID_SPEED, MAX_ASTEROID_SPEED) - AVERAGE_ASTEROID_SPEED
      )
    )
  }
}
