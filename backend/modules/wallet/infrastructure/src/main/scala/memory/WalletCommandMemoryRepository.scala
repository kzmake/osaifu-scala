package infrastructure.memory

import domain.entity._
import domain.vo._
import domain.repository._

import scala.concurrent.{ExecutionContext, Future}

class WalletCommandMemoryRepository()(implicit val ec: ExecutionContext) extends WalletRepository {
  override def save(entity: Wallet): Future[Wallet] =
    for {
      _ <- Future.successful(entity)
    } yield entity

  override def get(id: WalletId): Future[Option[Wallet]] = {
    for {
      fakeWallet <- Future.successful(Some(Wallet.apply(owner = Owner("alice"), balance = Money(1000))))
    } yield fakeWallet
  }

  override def delete(entity: Wallet): Future[Wallet] =
    for {
      _ <- Future.successful(entity)
    } yield entity
}
