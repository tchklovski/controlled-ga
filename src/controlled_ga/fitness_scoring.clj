(ns controlled-ga.fitness-scoring
  (:use [clojure.math.numeric-tower :only [expt]]))

(defn run-individual
  [cand input]
  ;; for our candidates, input is a sequence of input values for the candidate's :fn
  ;; note that it's possible that running cand's fn raises an exception
  (map (:fn cand) input))

(def +infinity Double/POSITIVE_INFINITY)

(defn rmse
  "root mean squared error; if actualvalues have non-numbers, those will be replaced with +inifnity"
  ([actualvalues targetvalues]
     (let [check #(if (number? %) % +infinity)]
       (rmse (map - (map check actualvalues) (map check targetvalues)))))
  ([errors]
      (let [sqr  #(expt % 2)
            sqrt #(expt % 0.5)
            mean #(/ (apply + %) (count %))]
        (sqrt (mean (map sqr errors))))))

(defn make-fitness-fn
  "returns a function that takes a candidate (with a :fn) and returns that candidate's error"
  ;; could return more than error -- observations on improvement
  [target]
  (let [test-input (range 0 1 0.01) ;; (repeatedly 100 rand)
        run-on-test-input #(run-individual % test-input)
        target-output (run-on-test-input target)]
    (fn [candidate]
      (rmse (run-on-test-input candidate) target-output))))
