(ns controlled-ga.mutation)

;; this is mutation in evolutionary sense, not data structure sense

(def mutate-expression-aux)

(defn mutate
  "deep, probabilistic mutation of the expression passed in"
  [expr]
  ;; terminate with a certain probability
  (let [thresh 0.2]
    (if (< (rand) thresh)
      expr
      (mutate-expression-aux expr))))

;; visit aspects of the expression, mutating those

;; can mutate just one thing per generation (or zero)

;; mutator machinery vs. an actual (evolvable) mutator

(defn mutate-expression-aux
  [expr]
  1)

(defmulti mutate class)
(defmethod mutate
    Number [n]
    (+ n 1))

