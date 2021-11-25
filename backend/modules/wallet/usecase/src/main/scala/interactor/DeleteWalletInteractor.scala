package interactor

import vo.WalletId
import repository.WalletRepository
import port.{DeleteWalletPort, DeleteWalletInputData, DeleteWalletOutputData}
import org.atnos.eff.Eff
import org.atnos.eff.future._future
import interactor.usecase.{_useCaseEither, FutureOps, FutureOptionOps}

import scala.concurrent.ExecutionContext

class DeleteWalletInteractor(walletRepository: WalletRepository)(implicit ec: ExecutionContext)
    extends DeleteWalletPort {
  def program[R: _future: _useCaseEither](in: DeleteWalletInputData): Eff[R, DeleteWalletOutputData] =
    for {
      wallet <- walletRepository.get(WalletId.apply(in.id)).toUseCaseErrorIfNotExists("wallet").toEff
      _      <- walletRepository.delete(wallet).raiseIfFutureFailed("wallet").toEff
    } yield DeleteWalletOutputData.apply()
}
