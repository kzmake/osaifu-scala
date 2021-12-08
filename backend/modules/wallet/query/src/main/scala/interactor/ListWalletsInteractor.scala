package query.interactor

import query.repository._
import query.port._
import query.eff.Query._

import org.atnos.eff.Eff
import org.atnos.eff.future._future

import scala.concurrent.ExecutionContext

final class ListWalletsInteractor(walletRepository: WalletRepository)(implicit ec: ExecutionContext)
    extends ListWalletsPort {
  def program[R: _future: _queryEither](in: ListWalletsInputData): Eff[R, ListWalletsOutputData] =
    for {
      listedWallets <- walletRepository.list().toEff
    } yield ListWalletsOutputData.apply(listedWallets)
}
