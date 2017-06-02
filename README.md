# marriage-allowance-des-stub

[![Build Status](https://travis-ci.org/hmrc/marriage-allowance-des-stub.svg)](https://travis-ci.org/hmrc/marriage-allowance-des-stub) [ ![Download](https://api.bintray.com/packages/hmrc/releases/marriage-allowance-des-stub/images/download.svg) ](https://bintray.com/hmrc/releases/marriage-allowance-des-stub/_latestVersion)

This is a placeholder README.md for a new repository

### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html")

# Marriage Allowance DES Stub

The External Test DES Stub is a service to support stateful sandbox testing in the
External Test environment. It stubs the behaviour of DES in order that an API microservice
is able to implement only a single set of routes regardless of whether it is being called
in a test or production environment.

## What uses this service?
API microservices deployed to the External Test environment should be configured to connect
to this stub instead of a real DES.

API microservices which this stubs behaviour for are:
* [Individual Benefits](https://github.tools.tax.service.gov.uk/HMRC/individual-benefits)


## What does this service use?
* [Datastream](https://github.tools.tax.service.gov.uk/HMRC/datastream) (for Audit)
* Metrics/Grafana

## Running the tests
```
./run_all_test.sh
```

## Running the service locally

To run the service locally on port `9650`:
```
./run_local.sh
```

To test the stub endpoint for Individual Benefits:
```
curl -X GET http://localhost:9650/self-assessment-prepop/individual/2234567890/benefits/tax-year/2014-15