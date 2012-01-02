(ns controlled-ga.test.fitness-scoring
  (:use [controlled-ga.fitness-scoring])
  (:use [clojure.test]))

(deftest test-compute-fitness
  (is (= 0.0 (compute-fitness [1 4 9] [1 2 3] #(* % %))) "Squares should work"))

(deftest test-compute-fitnesses
  (is (= [0.0 1.0] (compute-fitnesses #(+ %) [#(+ %) #(+ % 1)])) "Fintesses should work"))
