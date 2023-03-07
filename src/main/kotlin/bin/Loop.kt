package bin

import lib.Engine
import lib.Field
import lib.HumanUser
import org.openrndr.application

fun main() = application {

  val field = Field.defaultRandom()
  val user = HumanUser()
  val engine = Engine(WIDTH.toDouble(), HEIGHT.toDouble(), user, field)

  configure {
    width = WIDTH
    height = HEIGHT
    windowResizable = false
  }

  program {

    user.program = this

    extend {
      if (!engine.finished) {
        engine.step()
        engine.draw(this)
      }
    }
  }
}

