package port

import entity.Wallet

trait CreateWalletPort extends Port[CreateWalletInputData, CreateWalletOutputData]

case class CreateWalletInputData(
    _empty: String
) extends AnyVal
    with InputData
case class CreateWalletOutputData(
    wallet: Wallet
) extends AnyVal
    with OutputData
