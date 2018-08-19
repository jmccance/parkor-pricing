package parkor.config

import parkor.controllers.RateController
import parkor.cors.scaladsl.CorsDirectives._
import parkor.services.{RateService, RateServiceImpl}
import parkor.web.{Middleware, Server}
import scaldi.Module

class ApplicationModule extends Module {
  bind[ApplicationConfig] to
    pureconfig.loadConfigOrThrow[ApplicationConfig]("parkor")

  bind[RateConfig] to readRateConfig(
    inject[ApplicationConfig].rateConfig,
    { errors =>
      // TODO 
      println(errors.show)
      sys.exit(1)
    }
  )

  bind[Middleware] to Middleware(cors())

  bind[RateService] to new RateServiceImpl(inject[RateConfig].toRates)

  bind[RateController] to injected[RateController]

  bind[Server] to injected[Server]

}
