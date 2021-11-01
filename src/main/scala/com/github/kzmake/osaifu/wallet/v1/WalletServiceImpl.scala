package com.github.kzmake.osaifu.wallet.v1

import scala.concurrent.Future

class WalletServiceImpl() extends WalletService {
  override def create(request: CreateRequest): Future[CreateResponse] = {
    Future.successful(
      CreateResponse(Some(Wallet(id = "dummyid", owner = "alice", balance = "2000")))
    )
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
