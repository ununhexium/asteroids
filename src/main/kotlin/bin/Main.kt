package bin

import ai.retard.Gene
import ai.retard.Genetic
import lib.Engine
import lib.Field
import lib.HumanUser
import lib.OpenRndr
import java.time.Duration
import kotlin.random.Random

fun main() {

  val seed = 116L
  val genetic = Genetic(96, seed)

  val user = genetic.process(Duration.ofSeconds(50))
//  val user = HumanUser()

  val field = Field.constant(Random(seed))
  val renderer = OpenRndr(field, user, WIDTH.toDouble(), HEIGHT.toDouble())
  val engine =
    Engine(
      WIDTH.toDouble(),
      HEIGHT.toDouble(),
      user,
      field,
      renderer,
      if(user is HumanUser) Int.MAX_VALUE else (user as Gene).inputs.size
    )

  play(
    user,
    renderer,
    engine
  )
}

