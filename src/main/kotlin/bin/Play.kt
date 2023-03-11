package bin

import lib.*
import org.openrndr.Program
import org.openrndr.application

fun play(
  user: User,
  renderer: Renderer,
  engine: Engine<User>,
) {

  var lastFrame = 0.0
  var once = false

  application {

    configure {
      width = WIDTH
      height = HEIGHT
      windowResizable = false
    }

    program {

      if(!once) {
        once(this, user, renderer)
        once = true
      }

      extend {
        if (!engine.finished) {
          engine.step()
          lastFrame = seconds
        } else {
          if (seconds - lastFrame > SHOW_AFTER_DEATH) this.application.exit()
          renderer.draw()
        }
      }
    }
  }
}

private fun once(
  program: Program,
  user: User,
  renderer: Renderer
) {

  when (user) {
    is HumanUser -> user.program = program
  }

  when (renderer) {
    is OpenRndr -> renderer.program = program
  }
}
