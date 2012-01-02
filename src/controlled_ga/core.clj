(ns controlled-ga.core
  (:use [clojure.math.numeric-tower :only [expt]]
        [controlled-ga scoring mutation]
        ))

(defn target-fn
  [x]
  (+ (* x x) 0.5))


(def raw-candidates
     '[x
       (* x x)])

(defn make-candidate
  [cand]
  (let [make-fn (memoize #(eval (list 'fn '[x] %)))]
    {:expr cand
     :fn (make-fn cand)}))

(defn score-raw-candidates
  [raw-candidates target-fn]
  (let [candidates (map make-candidate raw-candidates)]
    (score-candidates target-fn candidates)))

(defn show-scored-candidates
  [cands]
  (map #(vector (:err %) (:expr %)) cands))
