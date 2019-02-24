# Port Scanner

<p align="center">
Multithreaded Java app to scan all ports within specified IP.
</br>
</br>

<a href="https://opensource.org/licenses/MIT">
  <img alt="license: MIT" src="https://img.shields.io/badge/License-MIT-yellow.svg">
</a>
<a href="https://travis-ci.com/Bartosz-D3V/port-scanner">
  <img alt="ci status" src="https://travis-ci.com/Bartosz-D3V/port-scanner.svg?token=tqZyPRhzSnop7iN2Y7Ug&branch=master">
</a>
</br>
</br>
</p>

## About
Run app using MVN (see below), or simply start Main.java file using java, or your favourite IDE.
Application will prompt for IP you want to scan and start scanning all ports (from 0 to 65535).

*Word of caution*
Scanning port other than your local one might attract attention of third party services.

## Install

```bash
mvn install -DskipTests
```

## Run

```bash
mvn compile exec:java
```

## Test

```bash
mvn test
```
