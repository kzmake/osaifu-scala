package com.github.kzmake.osaifu

import com.github.kzmake.osaifu.wallet.v1._

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.model.HttpResponse
import akka.grpc.scaladsl.ServerReflection
import akka.grpc.scaladsl.ServiceHandler
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import com.typesafe.config.ConfigFactory

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

object Application extends App {
  val conf = ConfigFactory
    .parseString("akka.http.server.preview.enable-http2 = on")
    .withFallback(ConfigFactory.defaultApplication())
  implicit val sys                  = ActorSystem[Nothing](Behaviors.empty, "Osaifu", conf)
  implicit val ec: ExecutionContext = sys.executionContext

  val walletService: PartialFunction[HttpRequest, Future[HttpResponse]] =
    WalletServiceHandler.partial(new WalletServiceImpl())
  val reflectionService = ServerReflection.partial(List(WalletService))
  val serviceHandlers: HttpRequest => Future[HttpResponse] =
    ServiceHandler.concatOrNotFound(walletService, reflectionService)

  Http()
    .newServerAt("0.0.0.0", 50051)
    .bind(serviceHandlers)
    .foreach { binding => println(s"gRPC server bound to: ${binding.localAddress}") }
}
