(ns controlled-ga.core
  (:use [controlled-ga fitness-scoring mutation]
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
  (map #(vec (map % [:err :expr])) cands))

; (in-ns 'controlled-ga.core)
;(use '(incanter core stats charts))
;(view (function-plot target-fn 0 1))