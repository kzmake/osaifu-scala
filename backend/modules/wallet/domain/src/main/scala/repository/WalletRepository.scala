package domain.repository

import domain.entity._
import domain.vo._

import scala.concurrent.Future

trait WalletRepository {
  def save(entity: Wallet): Future[Wallet]
  def get(id: WalletId): Future[Option[Wallet]]
  def delete(entity: Wallet): Future[Wallet]
}
