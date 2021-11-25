package grpc.wallet.v1

import api.osaifu.wallet.v1._

import memory.WalletMemoryRepository
import repository.WalletRepository
import interactor.CreateWalletInteractor
import interactor.DeleteWalletInteractor
import controller.wallet.v1.WalletServiceController
import akka.actor.typed.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.model.HttpResponse

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

class WalletServer(system: ActorSystem[_]) {
  def run(): Future[Http.ServerBinding] = {
    implicit val sys: ActorSystem[_]  = system
    implicit val ec: ExecutionContext = system.executionContext

    val walletRepository: WalletRepository = new WalletMemoryRepository()
    val create: CreateWalletInteractor     = new CreateWalletInteractor(walletRepository)
    val delete: DeleteWalletInteractor     = new DeleteWalletInteractor(walletRepository)

    val service: HttpRequest => Future[HttpResponse] =
      WalletServiceHandler(new WalletServiceController(create, delete))

    val binding = Http().newServerAt("127.0.0.1", 50051).bind(service)

    binding.foreach { binding => println(s"gRPC server bound to: ${binding.localAddress}") }

    binding
  }
}
