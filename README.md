# Assignments

This directory contains all assignment solutions for Princeton's Algorithms 2 on Coursera.

| Module      | Assignment           | Specification                                                                     | Grade |
| ----------- | -------------------- | --------------------------------------------------------------------------------- | ----- |
| assignment1 | WordNet              | [Specification](http://coursera.cs.princeton.edu/algs4/assignments/wordnet.html)  | 100 % |
| assignment2 | Seam Carving         | [Specification](http://coursera.cs.princeton.edu/algs4/assignments/seam.html)     | 100 % |
| assignment3 | Baseball Elimination | [Specification](http://coursera.cs.princeton.edu/algs4/assignments/baseball.html) | 100 % |
| assignment4 | Boggle               | [Specification](http://coursera.cs.princeton.edu/algs4/assignments/boggle.html)   | 100 % |
| assignment5 | Burrows-Wheeler      | [Specification](http://coursera.cs.princeton.edu/algs4/assignments/burrows.html)  | 100 % |

## Project structure

All assignments are separate maven modules. 

The root pom.xml (in assignments) aggregates all assignment modules and contains logic 
that creates the submission zip file in the `target` directory on `mvn package`.

## Tests

Unit tests can be executed via maven-surefire-plugin.

```bash
cd assignments
mvn test
```

## Dependencies

All dependencies are defined in the root pom.xml. 
Submodules don't add any additional dependencies.

| GroupID           | ArtefactID           | Description                                                          |
| ----------------- | -------------------- | -------------------------------------------------------------------- |
| org.junit.jupiter | junit-jupiter-engine | Unit test framework                                                  |
| edu.princeton.cs  | algs4                | algs4 provided by the course from https://dl.bintray.com/algs4/maven |
