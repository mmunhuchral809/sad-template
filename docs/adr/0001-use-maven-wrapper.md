# ADR-0001 — Use the Maven Wrapper for all course builds

## Context
We are starting the course project with student groups working on macOS, Windows, and Linux. Reproducible builds without a global Maven installation are mandatory.

## Choice
We exclusively use `./mvnw` (Maven Wrapper 3.9.x). Any global `mvn` installation is secondary.

## Consequence
+ Builds are deterministic.
+ CI Pipeline reproduces the exact same setup.
- The wrapper version must be adjusted during Java updates (every 6 months).
