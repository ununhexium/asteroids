package lib

import org.openrndr.math.Vector2
import org.openrndr.math.mix
import org.openrndr.shape.Circle
import org.openrndr.shape.LineSegment
import kotlin.math.sqrt

fun LineSegment.intersections(cir: Circle): List<Vector2> {
  val d = end - start
  val f = start - cir.center

  val result = mutableListOf<Vector2>()

  val a = d.dot(d)
  val b = 2 * f.dot(d)
  val c = f.dot(f) - cir.radius * cir.radius

  val discriminant = b * b - 4 * a * c
  if (discriminant < 0) {
    return result
  }

  val length = sqrt(discriminant)

  val t1 = (-b - length) / (2 * a)
  val t2 = (-b + length) / (2 * a)

  if (t1 in 0.0..1.0) {
    result.add(mix(start, end, t1))
  }

  if (t2 in 0.0..1.0) {
    result.add(mix(start, end, t2))
  }

  return result
}

fun LineSegment.intersects(cir: Circle): Boolean {
  val d = end - start
  val f = start - cir.center

  val a = d.dot(d)
  val b = 2 * f.dot(d)
  val c = f.dot(f) - cir.radius * cir.radius

  val discriminant = b * b - 4 * a * c

  return discriminant < 0
}

