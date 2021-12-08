package com.github.kzmake.osaifu

import api.osaifu.wallet.v1._

import infrastructure.memory._
import domain.repository.{WalletRepository => WalletCommandRepository}
import query.repository.{WalletRepository => WalletQueryRepository}
import query.interactor._
import usecase.interactor._
import interface.controller.wallet.v1._

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.model.HttpResponse
import akka.grpc.scaladsl.ServerReflection
import akka.grpc.scaladsl.ServiceHandler
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import com.typesafe.config.ConfigFactory
import com.typesafe.config.Config

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

object Main extends App {
  val conf: Config = ConfigFactory
    .parseString("akka.http.server.preview.enable-http2 = on")
    .withFallback(ConfigFactory.defaultApplication())
  implicit val sys: ActorSystem[Nothing] = ActorSystem[Nothing](Behaviors.empty, "Osaifu", conf)
  implicit val ec: ExecutionContext      = sys.executionContext

  val walletQueryRepository: WalletQueryRepository = new WalletQueryMemoryRepository()
  val list: ListWalletsInteractor                  = new ListWalletsInteractor(walletQueryRepository)

  val walletCommandRepository: WalletCommandRepository = new WalletCommandMemoryRepository()
  val create: CreateWalletInteractor                   = new CreateWalletInteractor(walletCommandRepository)
  val delete: DeleteWalletInteractor                   = new DeleteWalletInteractor(walletCommandRepository)

  val walletService: PartialFunction[HttpRequest, Future[HttpResponse]] =
    WalletServiceHandler.partial(new WalletServiceController(list, create, delete))
  val reflectionService: PartialFunction[HttpRequest, Future[HttpResponse]] =
    ServerReflection.partial(List(WalletService))
  val serviceHandlers: HttpRequest => Future[HttpResponse] =
    ServiceHandler.concatOrNotFound(walletService, reflectionService)

  Http()
    .newServerAt("0.0.0.0", 50051)
    .bind(serviceHandlers)
    .foreach { binding => println(s"gRPC server bound to: ${binding.localAddress}") }
}
