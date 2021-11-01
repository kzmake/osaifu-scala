package com.github.kzmake.osaifu.wallet.v1

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.model.HttpResponse
import com.typesafe.config.ConfigFactory

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

object WalletServer {

  def main(args: Array[String]): Unit = {
    val conf = ConfigFactory
      .parseString("akka.http.server.preview.enable-http2 = on")
      .withFallback(ConfigFactory.defaultApplication())
    val system = ActorSystem[Nothing](Behaviors.empty, "WalletServer", conf)
    new WalletServer(system).run()
  }
}

class WalletServer(system: ActorSystem[_]) {
  def run(): Future[Http.ServerBinding] = {
    implicit val sys                  = system
    implicit val ec: ExecutionContext = system.executionContext

    val service: HttpRequest => Future[HttpResponse] =
      WalletServiceHandler(new WalletServiceImpl())

    val binding = Http().newServerAt("127.0.0.1", 50051).bind(service)

    binding.foreach { binding => println(s"gRPC server bound to: ${binding.localAddress}") }

    binding
  }
}
