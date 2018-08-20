package parkor.config

import parkor.web.controllers.RateController
import parkor.web.directives.CorsDirectives._
import parkor.services.{RateService, RateServiceImpl}
import parkor.web.{Middleware, Server}
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

  bind[Middleware] to Middleware(cors())

  bind[RateService] to new RateServiceImpl(inject[RateConfig].toRates)

  bind[RateController] to injected[RateController]

  bind[Server] to injected[Server]

}
