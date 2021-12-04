package entity

import vo._

case class Wallet(
    id: WalletId = WalletId.apply(),
    owner: Owner,
    balance: Money
) extends Entity[WalletId] {}
