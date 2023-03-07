package lib

import bin.SECONDS_PER_FRAME
import kotlin.math.max

data class Gun(
  val coolDownRate: Double,
  val heatingRate: Double,
  val maxHeat: Double,
  private var _heat: Double = 0.0,
) {
  val heatRatio
    get() = _heat / maxHeat

  fun canShoot() =
    _heat + heatingRate <= maxHeat

  fun shoot() {
    if(canShoot()) _heat += heatingRate
  }

  fun tick() {
    _heat = max(0.0, _heat - SECONDS_PER_FRAME * coolDownRate)
  }
}
