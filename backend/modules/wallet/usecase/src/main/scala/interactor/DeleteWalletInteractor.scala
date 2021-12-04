package usecase.interactor

import domain.vo._
import domain.repository._
import usecase.port._
import usecase.eff.Usecase.{_useCaseEither, FutureOps, FutureOptionOps}

import org.atnos.eff.Eff
import org.atnos.eff.future._future

import scala.concurrent.ExecutionContext

class DeleteWalletInteractor(walletRepository: WalletRepository)(implicit ec: ExecutionContext)
    extends DeleteWalletPort {
  def program[R: _future: _useCaseEither](in: DeleteWalletInputData): Eff[R, DeleteWalletOutputData] =
    for {
      wallet <- walletRepository.get(WalletId(in.id)).toUseCaseErrorIfNotExists("wallet").toEff
      _      <- walletRepository.delete(wallet).raiseIfFutureFailed("wallet").toEff
    } yield DeleteWalletOutputData.apply()
}
