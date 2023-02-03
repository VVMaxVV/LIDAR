package mapper

import model.AABBBox
import model.Point
import util.compareTo

internal class PointMapper {
    fun getAABBBox(points: List<Point>): AABBBox? {
        if (points.isEmpty()) return null

        var top = points[0].y
        var right = points[0].x
        var bottom = points[0].y
        var left = points[0].x

        points.forEach { point ->
            when {
                point.y < bottom -> bottom = point.y
                point.y > top -> top = point.y
            }
            when {
                point.x < left -> left = point.x
                point.x > right -> right = point.x
            }
        }

        return AABBBox(Point(left, top), Point(right, bottom))
    }
}
