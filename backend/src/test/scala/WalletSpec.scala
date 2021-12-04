package com.github.kzmake.osaifu

import api.osaifu.wallet.v1._

import akka.actor.testkit.typed.scaladsl.ActorTestKit
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.grpc.GrpcClientSettings
import com.typesafe.config.ConfigFactory
import akka.http.scaladsl.Http
import com.typesafe.config.Config

import org.scalatest.BeforeAndAfterAll
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.concurrent.duration._
import scala.concurrent.Future

class WalletSpec extends AnyWordSpec with BeforeAndAfterAll with Matchers with ScalaFutures {
  implicit val patience: PatienceConfig = PatienceConfig(scaled(5.seconds), scaled(100.millis))

  val conf: Config = ConfigFactory
    .parseString("akka.http.server.preview.enable-http2 = on")
    .withFallback(ConfigFactory.defaultApplication())

  val testKit: ActorTestKit = ActorTestKit(conf)

  val serverSystem: ActorSystem[_]      = testKit.system
  val bound: Future[Http.ServerBinding] = new WalletServer(serverSystem).run()

  bound.futureValue

  implicit val clientSystem: ActorSystem[_] = ActorSystem(Behaviors.empty, "WalletClient")

  val client: WalletServiceClient =
    WalletServiceClient(GrpcClientSettings.connectToServiceAt("127.0.0.1", 50001).withTls(false))

  override def afterAll(): Unit = {
    ActorTestKit.shutdown(clientSystem)
    testKit.shutdownTestKit()
  }

  "osaifu.wallet.v1/Create" should {
    "OKなレスポンスを返す" in {
      val response = client.create(CreateRequest(owner = "alice"))
      response.futureValue should ===(
        CreateResponse(
          Some(Wallet(id = response.futureValue.getWallet.id, owner = "alice", balance = "1000"))
        )
      )
    }
  }
}
