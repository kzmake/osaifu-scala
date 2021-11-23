package vo

import scala.math.BigDecimal

case class Money(value: BigDecimal) extends AnyVal with ValueObject[BigDecimal]
