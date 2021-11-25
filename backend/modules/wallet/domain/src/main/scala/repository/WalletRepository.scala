package repository

import entity.Wallet
import vo.WalletId

import scala.concurrent.Future

trait WalletRepository {
  def save(entity: Wallet): Future[Wallet]
  def get(id: WalletId): Future[Option[Wallet]]
  def delete(entity: Wallet): Future[Wallet]
}
