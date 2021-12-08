package interface.controller

import usecase.eff.Usecase.UseCaseEither
import query.eff.Query.QueryEither

import org.atnos.eff.{Fx, TimedFuture}

package object controller {
  type CommandFutureEitherStack = Fx.fx2[TimedFuture, UseCaseEither]
  type QueryFutureEitherStack   = Fx.fx2[TimedFuture, QueryEither]
}
