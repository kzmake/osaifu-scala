package controller

import org.atnos.eff.{Fx, TimedFuture}
import interactor.usecase.UseCaseEither

package object controller {
  type FutureEitherStack = Fx.fx2[TimedFuture, UseCaseEither]
}
