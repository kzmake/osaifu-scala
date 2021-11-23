package entity

import vo.Money
import vo.WalletId

case class Wallet(
    id: WalletId = WalletId.newId(),
    balance: Money = Money(1000)
) extends Entity[WalletId] {}
