package query.error

import cats.data.NonEmptyList

case class QueryError(error: NonEmptyList[String]) extends Throwable
object QueryError {
  def create(message: String): QueryError             = apply(NonEmptyList.one(message))
  def create(error: NonEmptyList[String]): QueryError = apply(error)
}
