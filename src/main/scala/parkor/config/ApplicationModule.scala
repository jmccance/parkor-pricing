package parkor.config

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.RejectionHandler
import parkor.services.{RateService, RateServiceImpl}
import parkor.web._
import parkor.web.controllers.RateController
import parkor.web.directives.CustomDirectives._
import scaldi.Module

class ApplicationModule extends Module {
  bind[ApplicationConfig] to
    pureconfig.loadConfigOrThrow[ApplicationConfig]("parkor")

  bind[RateConfig] to readRateConfig(
    inject[ApplicationConfig].rateConfig,
    { errors =>
      // TODO This error handling isn't ideal, but it's sufficient.
      // Ideally we would log an error (as opposed to println) and shutdown
      // gracefully.
      println(errors.show)
      sys.exit(1)
    }
  )

  bind[RejectionHandler] to
    rejectionHandler.withFallback(RejectionHandler.default)

  bind[Middleware] to Middleware(
    cors() &
      withRequestId &
      handleRejections(inject[RejectionHandler])
  )

  bind[RateService] to new RateServiceImpl(inject[RateConfig].toRates)

  bind[RateController] to injected[RateController]

  bind[Server] to injected[Server]

}
