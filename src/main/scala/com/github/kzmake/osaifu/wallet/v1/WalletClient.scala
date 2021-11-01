package com.github.kzmake.osaifu.wallet.v1

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.grpc.GrpcClientSettings

import scala.concurrent.ExecutionContext
import scala.util.Failure
import scala.util.Success

object WalletClient {

  def main(args: Array[String]): Unit = {
    implicit val sys: ActorSystem[_]  = ActorSystem(Behaviors.empty, "WalletClient")
    implicit val ec: ExecutionContext = sys.executionContext

    val client = WalletServiceClient(GrpcClientSettings.connectToServiceAt("127.0.0.1", 50051))

    val owners =
      if (args.isEmpty) List("alice", "bob")
      else args.toList

    owners.foreach(singleRequestReply)

    def singleRequestReply(owner: String): Unit = {
      println(s"Performing request: $owner")
      val reply = client.create(CreateRequest(owner))
      reply.onComplete {
        case Success(msg) =>
          println(msg)
        case Failure(e) =>
          println(s"Error: $e")
      }
    }
  }
}
