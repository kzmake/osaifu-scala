package memory

import entity.Wallet
import vo.WalletId
import repository.WalletRepository

import scala.concurrent.{ExecutionContext, Future}

class WalletMemoryRepository()(implicit val ec: ExecutionContext) extends WalletRepository {
  override def save(entity: Wallet): Future[Wallet] =
    for {
      _ <- Future.successful(entity)
    } yield entity

  override def get(id: WalletId): Future[Option[Wallet]] = {
    for {
      fakeWallet <- Future.successful(Some(Wallet.apply()))
    } yield fakeWallet
  }

  override def delete(entity: Wallet): Future[Wallet] =
    for {
      _ <- Future.successful(entity)
    } yield entity
}
