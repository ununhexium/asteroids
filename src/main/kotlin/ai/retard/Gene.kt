package ai.retard

import lib.User

data class Gene(val inputs: List<Input>) : User {
  override val up: Boolean
    get() = inputs[tick % inputs.size].up
  override val left: Boolean
    get() = inputs[tick % inputs.size].left
  override val right: Boolean
    get() = inputs[tick % inputs.size].right
  override val shoot: Boolean
    get() = inputs[tick % inputs.size].shoot

  var tick = 0
    private set

  override fun processInputs() {
    tick++
  }

  fun textActions() =
    inputs.map { it.toShortString() }
}
