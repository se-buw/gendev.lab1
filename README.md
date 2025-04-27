# Generative Software Engineering Lab 1

## 📝 [Report](../../blob/badges/report.md)

---

## Activity: Learn about EMF, class modeling, and code generation

All tutorials should work with most versions of Eclipse Modeling Tools, but the preferred version (the last one tested) is Eclipse 2025-03 Modeling.

Please find the tutorial here: [EMF Class Modeling and Code Generation Tutorial](https://se-buw.github.io/gse/tutorials/emf/)

-   Please work on items **1 to 8** in the attached tutorial.
-   Continue with items **13 and 14** in the attached tutorial (you may use [this project](https://github.com/se-buw/emf-tutorial-part1) for a working version of items 1 to 8).

If you find any inconsistencies inside the document, please post them in the discussion forum to get them fixed.

---

## Homework task: Create a class diagram and instances

Start from this provided template repository and import it into your Eclipse. Use the existing `model/lab1.ecore` model and the provided `model/lab1.genmodel`.

To pass this assignment, you need to:

-   Create a class diagram with at least 5 classes, 1 generalization, 8 attributes (in total), and 4 associations
-   Generate code for the class diagram
-   Add OCL constraints `c1` and `c2` to the class diagram, including at least one that:
    -   restricts the value of an attribute
    -   navigates over multiple associations
-   For each constraint, e.g., `c1`, create one instance `model/c1/fail.xmi` that fails validation against the OCL constraint `c1` and create one instance `model/c1/pass.xmi` that passes validation.
    -   The test cases in the project look for folders `c1` and `c2` to distinguish the two, but these are not related to the names of invariants in the Ecore model.

You may use the provided test cases to see your progress. All test cases should pass for your submission, but passing tests are not a sufficient criterion for passing the homework task.

See this video:

[![Video with a description of the homework task](https://img.youtube.com/vi/-0rJ1umkdUA/0.jpg)](https://www.youtube.com/watch?v=-0rJ1umkdUA)
