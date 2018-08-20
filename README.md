parkor-pricing
==============

A web service for retrieving an hourly rate given a time window.

Usage
-----

You can use `sbt` to run the application locally. By default it will bind to port 8080.

```sh
$ ./sbt run
```

This will use `./examples/rate-config.json`. To specify a custom rate configuration, use the `RATE_CONFIG` environment variable.

```sh
$ RATE_CONFIG=$PATH_TO_FILE ./sbt run
```

You can then interact with the API using any HTTP client, such as `curl`.

```sh
# A valid request
$ curl "http://localhost:8080/rates?start=2015-07-01T07:00:00Z&end=2015-07-01T12:00:00Z"
{"rate":1500}

# A valid request that returns XML
$ curl "http://localhost:8080/rates?start=2015-07-01T07:00:00Z&end=2015-07-01T12:00:00Z" \
       -H 'Accept: application/xml'
<RateResponse>
      <rate>
        1500
      </rate>
    </RateResponse>

# A valid request with no result
$ curl "http://localhost:8080/rates?start=2015-07-04T02:00:00Z&end=2015-07-04T03:00:00Z"
{"rate":null}

# An invalid request
$ curl "http://localhost:8080/rates?start=yesterday&end=tomorrow"

{"timestamp":"2018-08-20T16:31:10.130Z","error":"The query parameter 'start' was malformed: Text 'yesterday' could not be parsed at index 0"}
```

For more information on using the API, see the [OpenAPI](https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.1.md) specification, which is published at [http://localhost:8080/api-docs/openapi.yaml](http://localhost:8080/api-docs/openapi.yaml).


