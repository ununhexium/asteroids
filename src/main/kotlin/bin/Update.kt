package bin

fun updateSpaceship(width: Double, height: Double) {
  spaceship.updateAzimut(left = left, right = right)
  spaceship.updateSpeed(accelerate = up)
  spaceship.updateSight(width, height)
  spaceship.gun.tick()

  if(!spaceship.gun.canShoot()) {
    spaceship.shotAsteroid = null
  }

  if (shoot && spaceship.gun.canShoot()) {
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
