package bin

import lib.Engine
import lib.Field
import lib.HumanUser
import lib.OpenRndr
import org.openrndr.application

fun main() = application {

  val field = Field.defaultRandom()
  val user = HumanUser()
  val renderer = OpenRndr(field, user, WIDTH.toDouble(), HEIGHT.toDouble())
  val engine = Engine(WIDTH.toDouble(), HEIGHT.toDouble(), user, field, renderer)

  configure {
    width = WIDTH
    height = HEIGHT
    windowResizable = false
  }

  program {

    user.program = this
    renderer.program = this

    extend {
      if (!engine.finished) {
        engine.step()
      }
    }
  }
}

