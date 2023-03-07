package lib

import bin.HEIGHT
import bin.WIDTH
import kotlin.random.Random

data class World<U : User>(
  val field: Field,
  val user: U,
  val engine: Engine<U>
) {
  companion object {
    fun <U : User> make(seed: Long, user: U, maxTick: Int): World<U> {
      val random = Random(seed)

      val field = Field.constant(random)
      val renderer = NoRenderer
      val engine =
        Engine(
          WIDTH.toDouble(),
          HEIGHT.toDouble(),
          user,
          field,
          renderer,
          maxTick
        )

      return World(field, user, engine)
    }
  }
}