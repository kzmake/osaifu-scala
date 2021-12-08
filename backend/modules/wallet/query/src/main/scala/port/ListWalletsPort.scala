package query.port

import query.view._

trait ListWalletsPort extends Port[ListWalletsInputData, ListWalletsOutputData]

final case class ListWalletsInputData()                      extends InputData
final case class ListWalletsOutputData(wallets: Seq[Wallet]) extends OutputData
