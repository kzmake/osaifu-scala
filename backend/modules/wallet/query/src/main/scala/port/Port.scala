package query.port

import query.eff.Query._queryEither

import org.atnos.eff.Eff
import org.atnos.eff.future._future

trait Port[InputData, OutputData] {
  def program[R: _future: _queryEither](in: InputData): Eff[R, OutputData];
}
