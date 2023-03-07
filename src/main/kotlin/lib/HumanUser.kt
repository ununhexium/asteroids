package lib

import org.openrndr.KeyModifier
import org.openrndr.Program

class HumanUser : User {

  var _up = false
  var _left = false
  var _right = false
  var _shoot = false

  fun decideInputs(program: Program) {
    program.keyboard.keyDown.listen {
      when (it.key) {
        'W'.code -> {
          if (!_up) println("Accelerating")
          _up = true
        }

        'A'.code -> _left = true

        'D'.code -> _right = true
      }

      _shoot = KeyModifier.SHIFT in it.modifiers
    }

    program.keyboard.keyUp.listen {
      when (it.key) {
        'W'.code -> _up = false
        'A'.code -> _left = false
        'D'.code -> _right = false
      }

      _shoot = KeyModifier.SHIFT in it.modifiers
    }
  }

  override val up: Boolean
    get() = _up

  override val left: Boolean
    get() = _left

  override val right: Boolean
    get() = _right

  override val shoot: Boolean
    get() = _shoot
}