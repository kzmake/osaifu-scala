package controller.wallet.v1

import api.osaifu.wallet.v1._

import controller.controller.FutureEitherStack
import interactor.CreateWalletInteractor
import org.atnos.eff.ExecutorServices
import org.atnos.eff.concurrent.Scheduler
import org.atnos.eff.syntax.all.toEitherEffectOps
import org.atnos.eff.syntax.future.toFutureOps
import error.UseCaseError

import scala.concurrent.{ExecutionContext, Future}

class WalletServiceController(
    create: CreateWalletInteractor
)(implicit ec: ExecutionContext)
    extends WalletService {
  override def create(_request: CreateRequest): Future[CreateResponse] = {
    implicit val scheduler: Scheduler = ExecutorServices.schedulerFromGlobalExecutionContext

    create
      .program[FutureEitherStack]()
      .runEither[UseCaseError]
      .runAsync
      .flatMap {
        case Right(out) =>
          Future.successful(
            CreateResponse(
              Some(
                Wallet(
                  id = out.id.value.toString(),
                  owner = "alice",
                  balance = out.balance.value.toString()
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
    Future.successful(DeleteResponse())
  }
}
