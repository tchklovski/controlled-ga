(ns controlled-ga.fitness-scoring
  (:use [clojure.math.numeric-tower :only [expt]]))

(def compute-fitnesses)
(def compute-fitness)

(defn rmse
  [errors]
  (let [sqr  #(expt % 2)
        sqrt #(expt % 0.5)
        mean #(/ (apply + %) (count %))]
    (sqrt (mean (map sqr errors)))))

(defn score-candidates
  "returns candidates with :err err-val added; sorts cands by increasing error"
  [target-fn candidates]
  (let [scores (compute-fitnesses target-fn (map :fn candidates))
        scored-candidates (map #(assoc %1 :err %2) candidates scores)]
    (sort-by :err scored-candidates)))

(defn compute-fitnesses
  [target-fn fns]
  (let [num-test-points 100
        generate-test-points #(range 0 1 (/ %)) ;; #(take % (repeatedly rand))
        testpoints (generate-test-points num-test-points)
        targetvalues (map target-fn testpoints)]
    (map (partial compute-fitness targetvalues testpoints)
         fns)))

(defn compute-fitness
  "compute fitness of one function"
  [targetvalues testpoints f]
  (let [actualvalues (map f testpoints)
        errors (map - actualvalues targetvalues)]
    (rmse errors)))