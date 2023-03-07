package bin

const val ASTEROIDS = 10

const val WIDTH = 1920
const val HEIGHT = 1080
const val FPS = 60
const val SECONDS_PER_FRAME = 1 / 60.0

const val BASE_SPEED = 1.0

const val MIN_ASTEROID_SPEED = BASE_SPEED * 0.5
const val MAX_ASTEROID_SPEED = BASE_SPEED * 2.0
const val AVERAGE_ASTEROID_SPEED =
  (MIN_ASTEROID_SPEED + MAX_ASTEROID_SPEED) * 0.5
const val MIN_ASTEROID_SIZE = 1
const val MAX_ASTEROID_SIZE = 5
const val ASTEROID_SPLIT_COUNT = 3
const val SHIP_SIZE = 10.0
const val START_ANGLE = 0.0
