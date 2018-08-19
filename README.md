parkor-pricing
==============

A web service for retrieving an hourly rate given a time window.

Usage
-----

You can use `sbt` to run the application locally. By default it will bind to port 8080.

```sh
./sbt run
```

This will use `./examples/rate-config.json`. To specify a custom rate configuration, use the `RATE_CONFIG` environment variable.

```sh
RATE_CONFIG=$PATH_TO_FILE ./sbt run
```

For more information on using the API, see the [OpenAPI](https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.1.md) specification in `api.yaml`.