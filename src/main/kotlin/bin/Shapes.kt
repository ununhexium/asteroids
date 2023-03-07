package bin

import org.openrndr.math.Vector2
import org.openrndr.shape.shape


val contactPointShape = shape {
  contour {
    moveTo(-10.0, -10.0)
    lineTo(cursor + Vector2(20.0, 20.0))
    close()
  }
  contour {
    moveTo(-10.0, 10.0)
    lineTo(cursor + Vector2(20.0, -20.0))
    close()
  }
}
