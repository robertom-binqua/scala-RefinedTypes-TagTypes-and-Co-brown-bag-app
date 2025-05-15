## Scala Refined Types, Tag Types and Co.

Hi there,

This is the project I presented during my brown bag session on Tuesday, 31 December 2024, as part of the DASS project. The original presentation was first delivered in 2022, using Scala 2.12, during my time at Capgemini. The updated version, with only minor adjustments, has been adapted for Scala 2.13 and was presented while I was working at Accenture in 2024.

The [presentation.html](./presentation.html) contains a few slides I showed during the presentation.

I wrote some tests and the best way to inspect, navigate and understand the code is to see and run the tests and play with them.

To implement some of my tests I used Scala Check - something I wanted to talk about but the time was not enough (maybe another
time). As someone once said, "... a small underestimated gem" - https://scalacheck.org for all the info about it.
Don't be scared if the test task produces a lot of output ... especially for the json part is doing a lot of tests. ;-)

The code is for scala 2.13 with SIP-23 - LITERAL-BASED SINGLETON TYPES - support. (the original code was for scala 2.12 without LITERAL-BASED SINGLETON TYPES)

The project uses an sbt multi-project because there is a limitation in Refined when you use custom predicate. In this case, the order of compilation is important and that is why you first build your model, and after you use it ... for example in the front-end.
(Between me and you, I don't think a multi-project sbt project is a bad idea anyway)

If you run the test task in sbt, it will fail (only the frontEnd part), and this has been done on purpose because the great job that Mr Be Venneborg did, to bring Refined types into the json and Play forms world is still WIP. (At least from what I understood the custom message part needs some work.)

```
...
[info] Run completed in 2 seconds, 653 milliseconds.
[info] Total number of tests run: 46
[info] Suites: completed 15, aborted 0
[info] Tests: succeeded 46, failed 0, canceled 0, ignored 0, pending 0
[info] All tests passed.
[info] Passed: Total 46, Failed 0, Errors 0, Passed 46
[error] (myAppFrontEnd / Test / test) sbt.TestsFailedException: Tests unsuccessful
...
```

I created also a small project specifically for [scala-2.13](https://github.com/robertom-binqua/scala-2.13-RefinedTypes-TagTypes-and-Co-brown-bag-app) to see what are the changes introduced by SIP-23. Very small.
Be careful because I activated a feature to print the scala compiler steps, so you can see when compilation fails if a literal value does not satisfy a predicate.
That means that the output from sbt test is a lot (not huge) even if there are only 4 tests

Where should you start from? In the same way I did at the brown bag:

1) Have a look at the short presentation.html (only few slides): it identifies the problem. As reported in slide 2, the documentation of cats.data.NonEmptyList (https://typelevel.org/cats/datatypes/nel.html) is the best explanation of what we would like to achieve ... more or less: "...demand more specific arguments".
2) When you finish with the presentation.html, start with the code .... with an attempt to model your business logic with AnAttemptOfUtr (find it in the source)
3) Check the test for further info and then ...
4) ... jump to Utr where things start getting serious (the smart constructor pattern, puts the creation of an object behind a validation to be sure you create an instance in the right way, preserving its invariant)
5) Don't forget to check always the test and then...
6) Let's introduce Refined library in model.companyNumber that show you how to define a custom Refined type.
7) Have a look at eu.timepit.refined.api.Refined: a Refined type is a simple Type Constructor with 2 type parameters, T (the value we want to refined) and P (a predicate that T has to satisfy before we can create an instance of our refined type).
   When T satisfied P a refined instance is created from the library for us. See the tests to find out how this is possible. Interesting a Refined type is a Value Class ... see point 15 (https://docs.scala-lang.org/overviews/core/value-classes.html).
8) and check always the tests to find out about model.companyNumber
9) See what Refined already define for us: a lot of predicates (see package eu.timepit.refined.predicates) and objects ready to use (see package eu.timepit.refined.types)
10) After that, have a look at all the examples in Example1_JourneyIdSpec, Example2_JourneyIdSpec ... etc etc to have an idea of how to define predicate in a declarative way.
11) Now theSpecialId. It introduces 2 things: 1) the strange type ...ClosedOpen[5,10] syntax and the need for tagging a type (have a look at UsingTypeTaggingWithRefinedSpec to find out more)
12) To explore and understand ClosedOpen[5,10] jump to WitnessExampleSpec where you can find a litteral number in a type position (maybe ... much better read also https://docs.scala-lang.org/sips/42.type.html)
13) See usingTypeTagging and usingTypeTaggingWithRefined to complete almost the tour.
14) To conclude, find out how you can use Mr. Be Venneborg work to define a json reader and writer in InputData companion object.
15) Almost finished, .... Value Classes (https://docs.scala-lang.org/overviews/core/value-classes.html - Scala 2.10) can be used as replacement for tagging: they can wrap a base type and create a new type, like tagging can do. But they cannot wrap another value class, so you cannot use them to wrap a refined type instance. That is why @newtype annotation option (https://github.com/estatico/scala-newtype) can help: you can have a Refined Type (always check the tests ;-)) and wrap 2 instances of it using the @newtype to create 2 distinguished types and obtain 2 "....more specific arguments" that was our initial goal.
16) Well done! Coffee time

(PS: Refined is heavily based on implicits: if you use Intellij, please use "Expand Implicit Hints" feature to see the implicit resolution result):

Before leaving you with the code, I will list some resources you could find useful:

1) https://github.com/fthomas/refined
2) https://books.underscore.io/scala-with-cats/scala-with-cats.pdf
3) https://typelevel.org/cats/resources_for_learners.html
4) https://books.underscore.io/shapeless-guide/shapeless-guide.pdf
5) https://docs.scala-lang.org/sips/42.type.html
6) https://docs.scala-lang.org/sips/value-classes.html
7) Search online for Refined Scala and you will find a lot of articles.

I hope you can find this project useful, and you can extract something interesting from it.

If you have any questions feel free to contact me via robertom@binqua.co.uk

Have a nice day

Roberto
