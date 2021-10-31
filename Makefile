SHELL = /bin/bash

.PHONY: all
all: fmt lint

.PHONY: compile
compile:
	sbt compile

.PHONY: run
run:
	sbt compile "runMain com.example.helloworld.GreeterServer"

.PHONY: fmt
fmt:
	sbt scalafixAll scalafmtAll

.PHONY: lint
lint:
	sbt "scalafixAll --check" scalafmtCheckAll

.PHONY: grpc
grpc:
	grpcurl -plaintext --proto ./src/main/protobuf/helloworld.proto -d '{"name": "alice"}' localhost:50051 GreeterService.SayHello
