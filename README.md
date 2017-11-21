## Description
CashMan microservice serves endpoints to dispense and load currency to the cash machine.

* Dispense cash.
* Load cash.

## How to build?
Building this micro-service is straight forward. Use following commands to pull the contents from github and start the build.

```
git clone https://github.com/haneesa/cashman.git
```

```
./gradlew build
```

## How to debug in an IDE (Eclipse)?
Clone the source code from the repo mentioned above and run below command.

```
./gradlew eclipse
```

Load this folder to eclipse as an existing project and configure it to use Gradle STS plugin.
Debug using boot view. By default this service runs in 8080 port.


## Running the micro-service
Running the micro-service is accomplished using the simple command listed below.
```
./gradlew bootRun
```

## Authors
@haneesa

