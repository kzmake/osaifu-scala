package com.github.kzmake.osaifu.wallet.v1

import akka.actor.testkit.typed.scaladsl.ActorTestKit
import akka.actor.typed.ActorSystem

import org.scalatest.BeforeAndAfterAll
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.concurrent.duration._

class WalletServiceImplSpec
    extends AnyWordSpec
    with BeforeAndAfterAll
    with Matchers
    with ScalaFutures {

  val testKit: ActorTestKit = ActorTestKit()

  implicit val patience: PatienceConfig = PatienceConfig(scaled(5.seconds), scaled(100.millis))

  implicit val sys: ActorSystem[_] = testKit.system

  val service = new WalletServiceImpl()

  override def afterAll(): Unit = {
    testKit.shutdownTestKit()
  }

  "WalletServiceImpl" should {
    "response to single request" in {
      val response = service.create(CreateRequest(owner = "alice"))
      response.futureValue should ===(
        CreateResponse(Some(Wallet(id = "dummyid", owner = "alice", balance = "2000")))
      )
    }
  }
}
//#full-example
