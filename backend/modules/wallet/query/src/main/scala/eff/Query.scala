package query.eff

import query.error.QueryError

import org.atnos.eff.future.{_future, fromFuture}
import org.atnos.eff.{|=, either, Eff}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

object Query {
  type QueryEither[T]  = Either[QueryError, T]
  type _queryEither[R] = QueryEither |= R

  implicit class FutureOptionOps[T](maybeFutureValue: Future[Option[T]])(implicit
      ec: ExecutionContext
  ) {
    def toQueryErrorIfNotExists(message: String): Future[T] =
      maybeFutureValue.transformWith {
        case Success(Some(value)) => Future.successful(value)
        case Success(None)        => Future.failed(QueryError.create(s"${message}の取得に失敗しました"))
        case Failure(exception)   => Future.failed(exception)
      }
  }

  implicit class FutureOps[T](futureValue: Future[T])(implicit ec: ExecutionContext) {
    def raiseIfFutureFailed(message: String): Future[T] =
      futureValue.transformWith {
        case Success(value) => Future.successful(value)
        case Failure(exception) =>
          Future.failed(QueryError.create(s"${message}の処理に失敗しました: ${exception.getMessage}"))
      }

    def toEff[R: _future]: Eff[R, T] = fromFuture(futureValue)
  }

  implicit class EitherQueryErrorOps[T](eitherValue: Either[QueryError, T]) {
    def toEff[R: _queryEither]: Eff[R, T] = either.fromEither(eitherValue)
  }
}
