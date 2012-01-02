(ns controlled-ga.scoring
  (:use [clojure.math.numeric-tower :only [expt]]))

(def compute-fitnesses)
(def compute-fitness)

(defn score-candidates
  "returns ([cand1 sc1] ...), sorted by increasing error"
  [target-fn candidates]
  (let [scores (compute-fitnesses target-fn (map :fn candidates))
        scored-candidates (map #(assoc %1 :err %2) candidates scores)
        ]
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
        score-one-point (fn [actual target] (expt (- actual target) 2))
        scores (map score-one-point actualvalues targetvalues)
        average #(/ (apply + %) (count %))
        normalize-average-score #(expt % 0.5)]
    (normalize-average-score (average scores))))