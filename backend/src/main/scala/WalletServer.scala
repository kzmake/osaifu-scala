package com.github.kzmake.osaifu

import api.osaifu.wallet.v1._

import infrastructure.memory._
import domain.repository.{WalletRepository => WalletCommandRepository}
import query.repository.{WalletRepository => WalletQueryRepository}
import query.interactor._
import usecase.interactor._
import interface.controller.wallet.v1._

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

    val walletQueryRepository: WalletQueryRepository = new WalletQueryMemoryRepository()
    val list: ListWalletsInteractor                  = new ListWalletsInteractor(walletQueryRepository)

    val walletCommandRepository: WalletCommandRepository = new WalletCommandMemoryRepository()
    val create: CreateWalletInteractor                   = new CreateWalletInteractor(walletCommandRepository)
    val delete: DeleteWalletInteractor                   = new DeleteWalletInteractor(walletCommandRepository)

    val service: HttpRequest => Future[HttpResponse] =
      WalletServiceHandler(new WalletServiceController(list, create, delete))

    val binding = Http().newServerAt("127.0.0.1", 50001).bind(service)

    binding.foreach { binding => println(s"gRPC server bound to: ${binding.localAddress}") }

    binding
  }
}
