package bin

import lib.Engine
import lib.HumanUser
import lib.OpenRndr
import org.openrndr.application

fun play(
  user: HumanUser,
  renderer: OpenRndr,
  engine: Engine<HumanUser>,
  lastFrame: Double
) {
  var lastFrame1 = lastFrame
  application {

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
          lastFrame1 = seconds
        } else {
          if (seconds - lastFrame1 > SHOW_AFTER_DEATH) this.application.exit()
          renderer.draw()
        }
      }
    }
  }
}
