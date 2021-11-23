package vo

trait ValueObject[T] extends Any {
  def value: T
}
