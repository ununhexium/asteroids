package bin

import lib.Asteroid
import lib.Spaceship
import org.openrndr.extra.noise.random
import org.openrndr.math.Vector2


val asteroids = (1..ASTEROIDS).map {
  val averageSpeed = (MIN_ASTEROID_SPEED + MAX_ASTEROID_SPEED) / 2
  Asteroid(
    Vector2(
      random(0.0, WIDTH.toDouble()),
      random(0.0, HEIGHT.toDouble())
    ),
    random(MIN_ASTEROID_SIZE.toDouble(), MAX_ASTEROID_SIZE.toDouble()).toInt(),
    Vector2(
      random(MIN_ASTEROID_SPEED, MAX_ASTEROID_SPEED) - averageSpeed,
      random(MIN_ASTEROID_SPEED, MAX_ASTEROID_SPEED) - averageSpeed,
    )
  )
}.toMutableList()

val spaceship = Spaceship(Vector2.ZERO, 0.0)
