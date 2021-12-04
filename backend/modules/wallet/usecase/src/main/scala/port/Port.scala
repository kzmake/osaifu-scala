package usecase.port

import usecase.eff.Usecase._useCaseEither

import org.atnos.eff.Eff
import org.atnos.eff.future._future

trait Port[InputData, OutputData] {
  def program[R: _future: _useCaseEither](in: InputData): Eff[R, OutputData];
}
