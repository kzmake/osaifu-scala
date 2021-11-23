SHELL = /bin/bash

.PHONY: all
all: proto fmt lint

.PHONY: compile
compile:
	sbt compile

.PHONY: test
test: compile
	sbt test

.PHONY: run
run: compile
	sbt run

.PHONY: proto
proto:
	buf generate

.PHONY: fmt
fmt:
	sbt scalafixAll scalafmtAll

.PHONY: lint
lint:
	sbt "scalafixAll --check" scalafmtCheckAll

.PHONY: dev
dev:
	skaffold dev

.PHONY: run
run:
	skaffold run --tail --port-forward=user

.PHONY: http
http:
	curl -s -X GET localhost:8080/v1/wallets | jq .
	curl -s -X GET localhost:8080/v1/wallets/dummyid | jq .
	curl -s -X POST localhost:8080/v1/wallets -H "Content-Type: application/json" -d '{"owner": "alice"}' | jq .
	curl -s -X DELETE localhost:8080/v1/wallets/dummyid | jq .


.PHONY: grpc
grpc:
	grpcurl -plaintext localhost:50051 osaifu.wallet.v1.WalletService/List
	grpcurl -plaintext -d '{"id": "dummyid"}' localhost:50051 osaifu.wallet.v1.WalletService/Get
	grpcurl -plaintext -d '{"owner": "alice"}' localhost:50051 osaifu.wallet.v1.WalletService/Create
	grpcurl -plaintext -d '{"id": "dummyid"}' localhost:50051 osaifu.wallet.v1.WalletService/Delete
