//#full-example
package com.example.helloworld

import akka.actor.testkit.typed.scaladsl.ActorTestKit
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.grpc.GrpcClientSettings

import com.typesafe.config.ConfigFactory

import org.scalatest.BeforeAndAfterAll
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.concurrent.duration._
import akka.http.scaladsl.Http
import com.typesafe.config.Config
import scala.concurrent.Future

class GreeterSpec extends AnyWordSpec with BeforeAndAfterAll with Matchers with ScalaFutures {

  implicit val patience: PatienceConfig = PatienceConfig(scaled(5.seconds), scaled(100.millis))

  // important to enable HTTP/2 in server ActorSystem's config
  val conf: Config = ConfigFactory
    .parseString("akka.http.server.preview.enable-http2 = on")
    .withFallback(ConfigFactory.defaultApplication())

  val testKit: ActorTestKit = ActorTestKit(conf)

  val serverSystem: ActorSystem[_]      = testKit.system
  val bound: Future[Http.ServerBinding] = new GreeterServer(serverSystem).run()

  // make sure server is bound before using client
  bound.futureValue

  implicit val clientSystem: ActorSystem[_] = ActorSystem(Behaviors.empty, "GreeterClient")

  val client: GreeterServiceClient =
    GreeterServiceClient(GrpcClientSettings.connectToServiceAt("127.0.0.1", 50051).withTls(false))

  override def afterAll(): Unit = {
    ActorTestKit.shutdown(clientSystem)
    testKit.shutdownTestKit()
  }

  "GreeterService" should {
    "reply to single request" in {
      val reply = client.sayHello(HelloRequest("Alice"))
      reply.futureValue should ===(HelloReply("Hello, Alice"))
    }
  }
}
//#full-example
