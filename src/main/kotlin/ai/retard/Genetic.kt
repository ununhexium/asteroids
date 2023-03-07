package ai.retard

import lib.User
import lib.World
import java.time.Duration
import java.time.Instant


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
        World.make(seed, gene, 300)
      }

      worlds.parallelStream().forEach {
        it.engine.fullRun()
      }

      val ordered = worlds
        .sortedByDescending { it.user.tick }
        .sortedBy { it.field.asteroidsWeightInLaserShots() }

      population = generateNextGeneration(ordered.map { it.user })

      val remaining = ordered.first().field.asteroidsWeightInLaserShots()
      val textActions = population.first().textActions()

      println("Best for $iteration: $remaining - $textActions")

      val inTime = Duration.between(start, Instant.now()) < timeLimit
    } while (inTime)

    return population.first()
  }

  private fun generateNextGeneration(ordered: List<Gene>): List<Gene> {
    val keep = ordered.takeLast(size / 2)
    val new = keep.map { it.copy(inputs = it.inputs + Input.random()) }

    return keep + new
  }

}