# controlled-ga

just trying to learn a little (more) clojure, and experiment with some
ideas about "accelerating search".

we search for approximators of a target function;
for example, we could search for
(fn [x] (* x x))
We assess functions using rmse on [0,1]

the crux is in the mutator that generates candidates.
We want to mutate the mutators (basically treating code as data),
identifying those that are able to improve how the search evolves.
there is also decoration of the thing being mutated to make it
evolve in more useful ways -- by eg having different levels
of mutability, or having silent genotype

## Usage


## Misc

- we should feed a bunch of vairables that the system is free to attend to or to ignore

- parsimomy reward is somewhat useful?

- can have a library of substitutions, with recognizers/advisers of when to try them.

- even more simply, could be approximating an irrational number like PI with algebraic expressions.

### Things to Demonstrate or Explore

the expression could capture how much of its own mutation it will permit --
it should evolve to solidify over time

## General Thoughts

it may be that trying is relatively cheap in computer realm, so selecting a good approach
(in a regime where computation is cheap vs. in humans, where action/computation is expensive)
is less important than just having enough of them and being able to try them

## License

Copyright (C) 2012 Timothy Chklovski

Distributed under the Eclipse Public License, the same as Clojure.
