SHELL = /bin/bash

.PHONY: all
all: fmt lint

.PHONY: compile
compile:
	sbt compile

.PHONY: run
run:
	sbt compile "runMain com.github.kzmake.osaifu.Application"

.PHONY: fmt
fmt:
	sbt scalafixAll scalafmtAll

.PHONY: lint
lint:
	sbt "scalafixAll --check" scalafmtCheckAll

.PHONY: grpc
grpc:
	@grpcurl -plaintext --proto ./api/osaifu/wallet/v1/wallet.proto -d '{"owner": "alice"}' localhost:50051 osaifu.wallet.v1.WalletService/Create
	@grpcurl -plaintext --proto ./api/osaifu/wallet/v1/wallet.proto -d '{"id": "dummyid"}' localhost:50051 osaifu.wallet.v1.WalletService/Get
	@grpcurl -plaintext --proto ./api/osaifu/wallet/v1/wallet.proto localhost:50051 osaifu.wallet.v1.WalletService/List
	@grpcurl -plaintext --proto ./api/osaifu/wallet/v1/wallet.proto -d '{"id": "dummyid"}' localhost:50051 osaifu.wallet.v1.WalletService/Delete
