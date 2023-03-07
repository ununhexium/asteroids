package bin

import org.openrndr.KeyModifier
import org.openrndr.Program

var finished = false
var up = false
var left = false
var right = false
var shoot = false

fun Program.processInputs() {
  keyboard.keyDown.listen {
    when (it.key) {
      'W'.code -> {
        if (!up) println("Accelerating")
        up = true
      }

      'A'.code -> left = true

      'D'.code -> right = true
    }

    shoot = KeyModifier.SHIFT in it.modifiers
  }

  keyboard.keyUp.listen {
    when (it.key) {
      'W'.code -> up = false
      'A'.code -> left = false
      'D'.code -> right = false
    }

    shoot = KeyModifier.SHIFT in it.modifiers
  }
}
