package lib

import bin.*
import org.openrndr.math.Vector2
import kotlin.random.Random

class Field(
  // TODO move the dimensions here
  val asteroids: MutableList<Asteroid>,
  val spaceship: Spaceship
) {

  companion object {
    fun defaultRandom(): Field {
      val rand = { min: Double, max: Double -> Random.nextDouble(min, max) }

      return Field(
        (1..ASTEROIDS).map {
          Asteroid(
            Vector2(
              rand(0.0, WIDTH.toDouble()),
              rand(0.0, HEIGHT.toDouble())
            ),
            rand(
              MIN_ASTEROID_SIZE.toDouble(),
              MAX_ASTEROID_SIZE.toDouble()
            ).toInt(),
            Vector2(
              rand(
                MIN_ASTEROID_SPEED,
                MAX_ASTEROID_SPEED
              ) - AVERAGE_ASTEROID_SPEED,
              rand(
                MIN_ASTEROID_SPEED,
                MAX_ASTEROID_SPEED
              ) - AVERAGE_ASTEROID_SPEED,
            )
          )
        }.toMutableList(),
        Spaceship(Vector2.ZERO, 0.0)
      )
    }

    fun constant(random: Random): Field {
      val rand = { min: Double, max: Double -> random.nextDouble(min, max) }

      return Field(
        (1..ASTEROIDS).map {
          Asteroid(
            Vector2(
              rand(0.0, WIDTH.toDouble()),
              rand(0.0, HEIGHT.toDouble())
            ),
            rand(
              MIN_ASTEROID_SIZE.toDouble(),
              MAX_ASTEROID_SIZE.toDouble()
            ).toInt(),
            Vector2(
              rand(
                MIN_ASTEROID_SPEED,
                MAX_ASTEROID_SPEED
              ) - AVERAGE_ASTEROID_SPEED,
              rand(
                MIN_ASTEROID_SPEED,
                MAX_ASTEROID_SPEED
              ) - AVERAGE_ASTEROID_SPEED,
            )
          )
        }.toMutableList(),
        Spaceship(Vector2.ZERO, 0.0)
      )
    }
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

  fun asteroidsWeightInLaserShots() =
    asteroids.sumOf { Asteroid.shotsToDestroy(it) }

  fun contact(): Boolean {
    val center = Vector2(WIDTH / 2.0, HEIGHT / 2.0)
    return asteroids.any { it.shape(WIDTH.toDouble(), HEIGHT.toDouble()).contains(center) }
  }

  fun getNearestAsteroid(): Asteroid {
    val center = Vector2(WIDTH / 2.0, HEIGHT / 2.0)
    return asteroids.minBy { it.getDisplayPosition(WIDTH.toDouble(), HEIGHT.toDouble()).distanceTo(center) }
  }
}
