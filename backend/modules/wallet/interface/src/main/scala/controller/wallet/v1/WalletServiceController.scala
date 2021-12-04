package interface.controller.wallet.v1

import api.osaifu.wallet.v1._

import interface.controller.controller._
import usecase.interactor._
import usecase.port._
import usecase.error.UseCaseError

import org.atnos.eff.ExecutorServices
import org.atnos.eff.concurrent.Scheduler
import org.atnos.eff.syntax.all.toEitherEffectOps
import org.atnos.eff.syntax.future.toFutureOps

import scala.concurrent.{ExecutionContext, Future}

class WalletServiceController(
    create: CreateWalletInteractor,
    delete: DeleteWalletInteractor
)(implicit ec: ExecutionContext)
    extends WalletService {
  override def create(_request: CreateRequest): Future[CreateResponse] = {
    implicit val scheduler: Scheduler = ExecutorServices.schedulerFromGlobalExecutionContext

    create
      .program[FutureEitherStack](CreateWalletInputData.apply())
      .runEither[UseCaseError]
      .runAsync
      .flatMap {
        case Right(out) =>
          Future.successful(
            CreateResponse(
              Some(
                Wallet(
                  id = out.wallet.id.value.toString(),
                  owner = "alice",
                  balance = out.wallet.balance.value.toString()
                )
              )
            )
          )
        case Left(e) => Future.failed(new Exception())
      }
  }

  override def get(request: GetRequest): Future[GetResponse] = {
    Future.successful(GetResponse(Some(Wallet(id = "dummyid", owner = "alice", balance = "2000"))))
  }

  override def list(request: ListRequest): Future[ListResponse] = {
    Future.successful(
      ListResponse(
        Seq(
          Wallet(id = "dummyid1", owner = "alice", balance = "2000"),
          Wallet(id = "dummyid2", owner = "bob", balance = "5000")
        )
      )
    )
  }

  override def delete(request: DeleteRequest): Future[DeleteResponse] = {
    implicit val scheduler: Scheduler = ExecutorServices.schedulerFromGlobalExecutionContext

    delete
      .program[FutureEitherStack](DeleteWalletInputData(id = request.id))
      .runEither[UseCaseError]
      .runAsync
      .flatMap {
        case Right(_) => Future.successful(DeleteResponse())
        case Left(e)  => Future.failed(new Exception())
      }
    Future.successful(DeleteResponse())
  }
}
