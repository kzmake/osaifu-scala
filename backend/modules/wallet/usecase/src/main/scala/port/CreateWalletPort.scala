package usecase.port

import domain.entity._

trait CreateWalletPort extends Port[CreateWalletInputData, CreateWalletOutputData]

case class CreateWalletInputData()                extends InputData
case class CreateWalletOutputData(wallet: Wallet) extends OutputData
