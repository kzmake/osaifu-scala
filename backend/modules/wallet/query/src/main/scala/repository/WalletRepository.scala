package query.repository

import query.view._

import scala.concurrent.Future

trait WalletRepository {
  def list(): Future[Seq[Wallet]]
  def find(id: String): Future[Option[Wallet]]
}
