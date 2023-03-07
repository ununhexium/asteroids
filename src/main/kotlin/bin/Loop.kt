import bin.Engine
import bin.HEIGHT
import bin.WIDTH
import lib.HumanUser
import org.openrndr.application

fun main() = application {

  val user = HumanUser()
  val engine = Engine(WIDTH.toDouble(), HEIGHT.toDouble(), user)

  configure {
    width = WIDTH
    height = HEIGHT
    windowResizable = false
  }

  program {

    user.program = this

    extend {

      user.processInputs()

      if (!engine.finished) {
        engine.step()
        engine.draw(this)
      }
    }
  }
}

