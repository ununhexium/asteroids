package ai.retard

import bin.HEIGHT
import bin.WIDTH
import bin.play
import lib.*
import java.time.Duration
import java.time.Instant
import kotlin.random.Random


/**
 * Genetic programming to find an input combination that works for a single game.
 */
class Genetic(val size: Int, val seed: Long) {

  fun process(timeLimit: Duration): User {
    val start = Instant.now()

    var population = (1..size).map {
      Gene(listOf(Input.random()))
    }

    var iteration = 0

    do {

      iteration++

      val worlds = population.map { gene ->
        World.make(seed, gene, 600)
      }
      val refField = Field.constant(Random(seed))

      worlds.parallelStream().forEach {
        it.engine.fullRun()
      }

      val maxShots = refField.asteroidsWeightInLaserShots()
      val ordered = worlds
        .sortedByDescending { it.user.inputs.size }

      population = generateNextGeneration(ordered.map { it.user })

      val best = ordered.first()
      val remaining = best.field.asteroidsWeightInLaserShots()

      println("Best for $iteration: $remaining - ${best.user.inputs.size}")

      val inTime = Duration.between(start, Instant.now()) < timeLimit
    } while (inTime)

    return population.first()
  }

  private fun generateNextGeneration(ordered: List<Gene>): List<Gene> {
    val keep = ordered.take(size / 2)
    val new = keep.map { it.copy(inputs = it.inputs + Input.fixed()) }

    return keep + new
  }

}
