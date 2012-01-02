(ns controlled-ga.core
  (:use [clojure.math.numeric-tower :only [expt]]
        [controlled-ga scoring mutation]
        ))

(defn target-fn
  [x]
  (+ (* x x) 0.5))


(def candidates
     '[x
       (* x x)])

(def make-fn
  (memoize #(eval (list 'fn '[x] %))))

(defn score-candidates
  []
  (let [candidate-fns (map make-fn candidates)]
    (compute-fitnesses target-fn candidate-fns)))

