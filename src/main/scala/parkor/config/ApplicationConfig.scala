package parkor.config

import java.io.File

case class ApplicationConfig(
  port: Int,
  rateConfig: File
)

