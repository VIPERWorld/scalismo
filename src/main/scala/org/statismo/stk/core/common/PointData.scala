package org.statismo.stk.core.common

import org.statismo.stk.core.geometry.Dim
import scala.reflect.ClassTag

trait PointData[D <: Dim, A] extends PartialFunction[Int, A] {
  def domain: DiscreteDomain[D]
  def values: Array[A]

 
  def apply(idx: Int): A = values(idx)
  def isDefinedAt(idx: Int) = idx >= 0 && idx <= values.size

  def foreach(f: A => A): Unit = values.foreach(f)
  

  override def hashCode = values.deep.hashCode
  override def equals(other: Any): Boolean = other match {
    case that: PointData[D, A] => {
      that.canEqual(this) && this.values.deep == that.values.deep && this.domain == that.domain
    }
    case _ => false
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[PointData[D, A]]
}

trait ScalarPointData[D <: Dim, @specialized(Float, Short) Pixel] extends PointData[D, Pixel] {
  def valueDimensionality : Int
  def map[Pixel2: ScalarValue: ClassTag](f: Pixel => Pixel2): ScalarPointData[D, Pixel2]

}

