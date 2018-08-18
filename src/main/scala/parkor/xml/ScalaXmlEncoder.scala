package parkor.xml

import scala.xml.NodeSeq

/** */
trait ScalaXmlEncoder[A] {
  def apply(a: A): NodeSeq
}
