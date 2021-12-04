package usecase.port

trait DeleteWalletPort extends Port[DeleteWalletInputData, DeleteWalletOutputData]

case class DeleteWalletInputData(id: String) extends InputData
case class DeleteWalletOutputData()          extends OutputData
