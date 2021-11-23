package port

import org.atnos.eff.Eff
import org.atnos.eff.future._future
import interactor.usecase._useCaseEither

trait Port[InputData, OutputData] {
  def program[R: _future: _useCaseEither](in: InputData): Eff[R, OutputData];
}
