SHELL = /bin/bash

.PHONY: all
all: fmt lint

.PHONY: run
run:
	sbt "runMain com.example.helloworld.GreeterServer"

.PHONY: fmt
fmt:
	sbt scalafixAll scalafmtAll

.PHONY: lint
lint:
	sbt "scalafixAll --check" scalafmtCheckAll

.PHONY: grpc
grpc:
	grpcurl --insecure --proto ./src/main/protobuf/helloworld.proto -d '{"name": "alice"}' localhost:8080 GreeterService.SayHello
