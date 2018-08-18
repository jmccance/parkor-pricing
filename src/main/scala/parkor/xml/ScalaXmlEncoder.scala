package parkor.xml

import scala.xml.NodeSeq

/** Type class providing a conversion from instances of a type `A` to
  * [[scala.xml.NodeSeq]].
  */
trait ScalaXmlEncoder[A] {
  def apply(a: A): NodeSeq
}
