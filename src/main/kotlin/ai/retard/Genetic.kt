package ai.retard

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
        World.make(seed, gene, population.maxOf { it.inputs.size })
      }
      val refField = Field.constant(Random(seed))

      worlds.parallelStream().forEach {
        it.engine.fullRun()
      }

      val ordered = worlds
        .filterNot { it.engine.lost }
        .sortedBy { it.field.asteroidsWeightInLaserShots() }

      population = generateNextGeneration(ordered.map { it.user }, size)

      val best = ordered.first()
      val remaining = best.field.asteroidsWeightInLaserShots()

      println("Best for $iteration: $remaining - ${best.user.inputs.size}")

      val inTime = Duration.between(start, Instant.now()) < timeLimit
    } while (inTime)

    println("Diversity: ${population.groupBy { it.inputs }.size}")

    return population.first()
  }

  private fun generateNextGeneration(
    ordered: List<Gene>,
    targetSize: Int
  ): List<Gene> {
    val eliminated = targetSize - ordered.size

    val nextStep = ordered.map { it.copy(inputs = it.inputs + Input.random()) }

    val new = (1..eliminated).map {
      val k = ordered.random()
      k.copy(inputs = k.inputs + Input.random())
    }

    return nextStep + new
  }

}
