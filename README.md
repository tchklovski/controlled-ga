# controlled-ga

just trying to learn a little (more) clojure, and experiment with some
ideas about "accelerating search".

we search for approximators of a target function;
for example, we could search for
(fn [x] (* x x))
We assess functions using rmse on [0,1]

the crux is in the mutator that generates candidates.
We want to mutate the mutators, identifying those that are able to improve
how the search evolves.

## Usage


## Misc

the expression could capture how much of its own mutation it will permit --
it should evolve to solidify over time

we should feed a bunch of vairables that the system is free to attend to or to ignore

parsimomy reward is somewhat useful?

can have a library of substitutions, with recognizers/advisers of when to try them.


even more simply, could be approximating an irrational number like PI with algebraic expressions.

two-var target fn:
(defn target-fn
  [a b]
  (let [dim 2]
    (expt (+
           (expt a dim)
           (expt b dim))
          (/ dim))))

## License

Copyright (C) 2012 Timothy Chklovski

Distributed under the Eclipse Public License, the same as Clojure.
