(ns controlled-ga.test.scoring
  (:use [controlled-ga.scoring])
  (:use [clojure.test]))

(deftest test-compute-fitness ;; FIXME: write
  (is (= 0.0 (compute-fitness [1 4 9] [1 2 3] #(* % %))) "Squares should work"))

(deftest test-compute-fitnesses ;; FIXME: write
  (is (= [0.0 1.0] (compute-fitnesses #(+ %) [#(+ %) #(+ % 1)])) "Fintesses should work"))
