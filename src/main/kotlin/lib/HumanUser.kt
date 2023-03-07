package lib

import org.openrndr.KeyModifier
import org.openrndr.Program

class HumanUser : User {

  lateinit var program:Program

  private var _up = false
  private var _left = false
  private var _right = false
  private var _shoot = false

  override fun processInputs() {
    program.keyboard.keyDown.listen {
      when (it.key) {
        'W'.code -> _up = true
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