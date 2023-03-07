package bin

import lib.User

fun updateSpaceship(width: Double, height: Double, user:User) {
  spaceship.updateAzimut(left = user.left, right = user.right)
  spaceship.updateSpeed(accelerate = user.up)
  spaceship.updateSight(width, height)
  spaceship.gun.tick()

  if(!spaceship.gun.canShoot()) {
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
