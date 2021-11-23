package vo

import java.util.UUID

case class WalletId(value: String) extends AnyVal with Id

object WalletId {
  def newId(): WalletId = WalletId(UUID.randomUUID().toString)
}
