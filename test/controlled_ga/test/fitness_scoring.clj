(ns controlled-ga.test.fitness-scoring
  (:use [controlled-ga.fitness-scoring])
  (:use [clojure.test]))

(deftest test-rmse
  (is (= 2.5 (rmse [3 4 0 0])) "rmse of errors")
  (is (= 2.5 (rmse [4 -5 1 1] [1 -1 1 1])) "rmse of actual vs target"))

(deftest test-compute-fitnesses
  (is (= [0.0 1.0] (compute-fitnesses #(+ %) [#(+ %) #(+ % 1)])) "Fintesses should work"))
