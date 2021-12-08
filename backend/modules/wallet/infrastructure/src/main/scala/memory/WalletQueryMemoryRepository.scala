package infrastructure.memory

import query.view._
import query.repository._

import scala.concurrent.{ExecutionContext, Future}

class WalletQueryMemoryRepository()(implicit val ec: ExecutionContext) extends WalletRepository {
  override def list(): Future[Seq[Wallet]] =
    for {
      ws <- Future.successful(
        Seq.apply(
          Wallet.apply(id = "fake1", owner = "alice", balance = "1000"),
          Wallet.apply(id = "fake2", owner = "bob", balance = "9999")
        )
      )
    } yield ws

  override def find(id: String): Future[Option[Wallet]] = {
    for {
      w <- Future.successful(Some(Wallet.apply(id = "fake1", owner = "alice", balance = "1000")))
    } yield w
  }
}
