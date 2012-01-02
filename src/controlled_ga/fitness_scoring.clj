(ns controlled-ga.fitness-scoring
  (:use [clojure.math.numeric-tower :only [expt]]))

(def compute-fitnesses)

(defn rmse
  ([actualvalues targetvalues]
     (rmse (map - actualvalues targetvalues)))
  ([errors]
      (let [sqr  #(expt % 2)
            sqrt #(expt % 0.5)
            mean #(/ (apply + %) (count %))]
        (sqrt (mean (map sqr errors))))))

(defn score-candidates
  "returns candidates with :err err-val added; sorts cands by increasing error"
  [target-fn candidates]
  (let [errors (compute-fitnesses target-fn (map :fn candidates))
        scored-candidates (map #(assoc %1 :err %2) candidates errors)]
    (sort-by :err scored-candidates)))

(defn compute-fitnesses
  [target-fn fns]
  (let [num-test-points 100
        generate-test-points #(range 0 1 (/ %)) ;; #(take % (repeatedly rand))
        testpoints (generate-test-points num-test-points)
        targetvalues (map target-fn testpoints)]
    (map #(rmse (map % testpoints) targetvalues) fns)))
