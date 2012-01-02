(ns controlled-ga.mutation)

(defn mutate-expression-aux
     [expr]
     1)

(defn mutate-expression
     [expr]
     ;; terminate with a certain probability
     (let [thresh 0.2]
       (if (< (rand) thresh)
         expr
         (mutate-expression-aux expr))))