package domain.vo

import java.util.UUID

case class WalletId(value: String) extends EntityId

object WalletId {
  def apply(): WalletId = WalletId.apply(UUID.randomUUID().toString)
}
