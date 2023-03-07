package lib

import bin.*
import org.openrndr.extra.noise.random
import org.openrndr.math.Vector2

class Field(
  val asteroids: MutableList<Asteroid>,
  val spaceship: Spaceship
) {
  companion object {
    fun defaultRandom() =
      Field(
        (1..ASTEROIDS).map {
          val averageSpeed = (MIN_ASTEROID_SPEED + MAX_ASTEROID_SPEED) / 2
          Asteroid(
            Vector2(
              random(0.0, WIDTH.toDouble()),
              random(0.0, HEIGHT.toDouble())
            ),
            random(
              MIN_ASTEROID_SIZE.toDouble(),
              MAX_ASTEROID_SIZE.toDouble()
            ).toInt(),
            Vector2(
              random(MIN_ASTEROID_SPEED, MAX_ASTEROID_SPEED) - averageSpeed,
              random(MIN_ASTEROID_SPEED, MAX_ASTEROID_SPEED) - averageSpeed,
            )
          )
        }.toMutableList(),
        Spaceship(Vector2.ZERO, 0.0)
      )
  }

  fun update(width: Double, height: Double, user: User) {
    updateSpaceship(width, height, user)
    updateAsteroids()
  }

  fun updateSpaceship(width: Double, height: Double, user: User) {
    spaceship.updateAzimut(left = user.left, right = user.right)
    spaceship.updateSpeed(accelerate = user.up)
    spaceship.updateSight(width, height, asteroids)
    spaceship.gun.tick()

    if (!spaceship.gun.canShoot()) {
      spaceship.shotAsteroid = null
    }

    if (user.shoot && spaceship.gun.canShoot()) {
      spaceship.gun.shoot()
      spaceship.shotAsteroid = spaceship.asteroidInSight
    }
  }

  fun updateAsteroids() {
    val shotAsteroid = spaceship.shotAsteroid
    if (shotAsteroid != null) {
      val old = asteroids.toList()
      asteroids.clear()
      asteroids.addAll(old.filter { it != shotAsteroid })
      asteroids.addAll(shotAsteroid.split())
    }
    asteroids.forEach {
      it.updatePosition(spaceship)
    }
  }
}
