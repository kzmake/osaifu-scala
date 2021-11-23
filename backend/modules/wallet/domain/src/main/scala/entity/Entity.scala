package entity

import vo.Id

trait Entity[ID <: Id] {
  val id: ID

  override def equals(obj: Any): Boolean =
    obj match {
      case that: Entity[_] => this.getClass == that.getClass && this.id == that.id
      case _               => false
    }

  override def hashCode(): Int = 31 * this.id.hashCode
}
