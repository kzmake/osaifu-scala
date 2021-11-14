FROM golang:1.17 as builder

WORKDIR /go/src/app

COPY go.mod .
COPY go.sum .
RUN go mod download

COPY . .
RUN go build -o /go/bin/app -ldflags '-s -w' main.go

FROM golang:1.17 as runner

COPY --from=builder /go/bin/app /app

CMD ["/app"]
