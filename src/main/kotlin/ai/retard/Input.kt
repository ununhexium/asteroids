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

    val inputs = listOf(true, false).flatMap { accel ->
      listOf(-1, 0, 1).flatMap { direction ->
        listOf(true, false).map { shoot ->
          Input(
            accel,
            when (direction) {
              -1 -> true
              1 -> false
              else -> false
            },
            when (direction) {
              -1 -> false
              1 -> true
              else -> false
            },
            shoot
          )
        }
      }
    }

    fun random() = inputs[Random.nextInt(inputs.size)]
    fun fixed() = Input(true, false, false, false)
  }
}
