(ns controlled-ga.fitness-scoring
  (:use [clojure.math.numeric-tower :only [expt]]))

;; FIXME -- this belongs in the individual defintion file
(defn run-individual
  [cand input]
  ;; for our candidates, input is a sequence of input values for the candidate's :fn
  (map (:fn cand) input))

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
  [fitness-fn candidates]
  (let [errors (map fitness-fn candidates)
        scored-candidates (map #(assoc %1 :err %2) candidates errors)]
    (sort-by :err scored-candidates)))

(defn make-fitness-fn
  "returns a function that takes a candidate (with a :fn) and returns that candidate's error"
  ;; could return more than error -- observations on improvement
  [target]
  (let [test-input (range 0 1 0.01) ;; (take 100 (repeatedly rand))
        run-on-test-input #(run-individual % test-input)
        target-output (run-on-test-input target)]
    (fn [candidate]
      (rmse (run-on-test-input candidate) target-output))))

;; simpler than improving a solution, there is simpliy "understanding" what's going on as an ai task --
;; making connections, providing explanations
;; it may be that trying is relatively cheap in computer realm, so selecting a good approach
;; is less important than just having enough of them and being able to try them
