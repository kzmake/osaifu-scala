package interactor

import entity._
import vo._
import repository.WalletRepository
import port.{CreateWalletPort, CreateWalletInputData, CreateWalletOutputData}
import org.atnos.eff.Eff
import org.atnos.eff.future._future
import interactor.usecase.{_useCaseEither, FutureOps, FutureOptionOps}

import scala.concurrent.ExecutionContext

class CreateWalletInteractor(walletRepository: WalletRepository)(implicit ec: ExecutionContext)
    extends CreateWalletPort {
  def program[R: _future: _useCaseEither](in: CreateWalletInputData): Eff[R, CreateWalletOutputData] =
    for {
      createdWallet <- walletRepository
        .save(Wallet.apply(owner = Owner("alice"), balance = Money(1000)))
        .raiseIfFutureFailed("wallet")
        .toEff
      gotWallet <- walletRepository.get(createdWallet.id).toUseCaseErrorIfNotExists("wallet").toEff
    } yield CreateWalletOutputData.apply(gotWallet)
}
