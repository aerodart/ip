# E.V.

[![Java CI](https://github.com/aerodart/ip/actions/workflows/gradle.yml/badge.svg)](https://github.com/aerodart/ip/actions/workflows/gradle.yml)

E.V. is a command-line task assistant that keeps track of your todos, deadlines
and events, and remembers them between sessions.

## Features

- Add todos, deadlines and events
- Mark tasks as done or not done
- Delete tasks from the registry
- Search tasks by keyword
- Automatic saving to and loading from `data/ev.txt`

## Getting started

Requires JDK 25.

Run from source:

```
./gradlew run
```

Or build a standalone jar and run that:

```
./gradlew shadowJar
java -jar build/libs/ev.jar
```

## Building

```
./gradlew build
```

This compiles the code, runs the unit tests and produces `build/libs/ev.jar`.
