# Kotlin Example Implementation of a Hexagonal/Onion/Clean Architecture

Inspired by https://github.com/thombergs/buckpal

## Tech Stack

* [Kotlin](https://kotlinlang.org)
* [Spock](https://github.com/spockframework/spock)
* [Micronaut](https://micronaut.io)
* [JDK 21+](https://www.oracle.com/java/technologies/downloads)
* [Virtual Threads](https://en.wikipedia.org/wiki/Virtual_thread)
* [Eclipsestore](https://eclipsestore.io)
* [IntelliJ IDEA Community Edition](https://www.jetbrains.com/idea/download)

## Layers and Dependency Inversion

![Dependency Inversion](di.png)

## Send Money Use Case

```gherkin
Feature: Send Money

  Scenario: Transaction succeeds
    Given a source account
    And a target account

    When money is send

    Then send money succeeds

    And source account is locked
    And source account withdrawal will succeed
    And source account is released

    And target account is locked
    And target account deposit will succeed
    And target account is released

    And accounts have been updated
```

# Gradle Examples

> ./gradlew check

> ./gradlew check -i

> ./gradlew cleanTest check -i

> ./gradlew check -t
