# Amőba (Gomoku) - Java 21 Maven Project

This is a CLI Amőba (Gomoku) game implemented in Java 21 with Maven, using SQLite for high-score persistence.
It follows the specification provided in `feladat.txt` and mirrors the structure/style of the referenced GitHub solution.

## Features
- NxM board (4 <= M <= N <= 25)
- X is human (starts automatically placed in center), O is random AI
- Players may only place next to existing pieces (including diagonally)
- Win by making five in a row (vertical, horizontal, diagonal)
- Save/load board from text file
- High scores stored in SQLite database
- Maven build with executable fat JAR (assembly plugin)

## Build & Run
```bash
mvn clean install
java -jar target/amoba-fat.jar
```

Database file `amoba.db` will be created on first run.

