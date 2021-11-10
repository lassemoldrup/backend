# Backend Projekt

Dette projekt bruger [Quarkus](https://quarkus.io/).

## Krav
Projektet kræver en installation af `JDK 11` som bla. kan hentes her https://adoptium.net/?variant=openjdk11.

## Kørsel

Du kan køre projektet i valgtfri terminal med kommandoen:

```shell script
gradlew quarkusDev
```

Bemærk at `$JAVA_HOME` environment variablen skal pege på gyldig Java 11 JDK installation.

Eksempel:
``` 
$ echo $JAVA_HOME
C:\Users\bdufjv\.jdks\temurin-11.0.12
```

# API Specifikation
Backenden udstiller et API som kan findes på http://localhost:8080/q/swagger-ui/

Og en specifikation af APIet (til brug i frontend hvis der er laves strukturelle ændringer) kan hentes her http://localhost:8080/q/openapi


## Relateret læsning

- RESTEasy JAX-RS ([guide](https://quarkus.io/guides/rest-json)): REST endpoint framework implementing JAX-RS and more
- [Quarkus](https://quarkus.io/)
