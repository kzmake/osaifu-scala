package interface.controller

import usecase.eff.Usecase.UseCaseEither

import org.atnos.eff.{Fx, TimedFuture}

package object controller {
  type FutureEitherStack = Fx.fx2[TimedFuture, UseCaseEither]
}
