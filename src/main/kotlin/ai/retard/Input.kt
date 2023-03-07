package ai.retard

import kotlin.random.Random

data class Input(
  val up: Boolean,
  val left: Boolean,
  val right: Boolean,
  val shoot: Boolean,
) {

  fun toShortString() =
    StringBuilder(3)
      .append(if (up) "↑" else " ")
      .append(if (left) "←" else if (right) "→" else " ")
      .append(if (shoot) "\uD83D\uDCA2" else " ")
      .toString()

  companion object {
    fun random(): Input {
      val turn = Random.nextBoolean()
      val left = Random.nextBoolean()
      val accelerate = Random.nextBoolean()
      val shoot = Random.nextBoolean()

      return Input(
        turn && left,
        turn && !left,
        accelerate,
        shoot,
      )
    }
  }
}
