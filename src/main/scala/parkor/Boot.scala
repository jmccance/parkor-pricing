package parkor

import parkor.config._
import parkor.web.Server
import scaldi.Injectable

object Boot extends Injectable {
  def main(args: Array[String]) {
    implicit val injector: ApplicationModule = new ApplicationModule

    val config = inject[ApplicationConfig]
    val server = inject[Server]

    server.startServer("localhost", config.port)
  }
}
