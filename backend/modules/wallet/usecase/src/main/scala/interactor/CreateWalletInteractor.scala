package interactor

import entity.Wallet
import repository.WalletRepository
import org.atnos.eff.Eff
import org.atnos.eff.future._future
import interactor.usecase.{_useCaseEither, FutureOps, FutureOptionOps}

import scala.concurrent.ExecutionContext

class CreateWalletInteractor(walletRepository: WalletRepository)(implicit
    ec: ExecutionContext
) {
  def program[R: _future: _useCaseEither](): Eff[R, Wallet] =
    for {
      createdWallet <- walletRepository.save(Wallet.apply()).raiseIfFutureFailed("wallet").toEff
      gotWallet <- walletRepository.get(createdWallet.id).toUseCaseErrorIfNotExists("wallet").toEff
    } yield gotWallet
}