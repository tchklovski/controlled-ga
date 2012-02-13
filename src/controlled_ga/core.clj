(ns controlled-ga.core
  (:use [controlled-ga fitness-scoring mutation]))

(def target-spec
  '(+ (* x x) 0.5))

(def candidate-specs
     '[x
       (* x x)])

(defn make-individual
  [spec]
  (let [make-fn (memoize #(eval (list 'fn '[x] %)))]
    {:spec spec
     :fn (make-fn spec)}))

(def target (make-individual target-spec))
(def candidates (map make-individual candidate-specs))

(def fitness-fn (make-fitness-fn target))


(defn show-scored-candidates
  [cands]
  (map #(vec (map % [:err :spec])) cands))

;; (in-ns 'controlled-ga.core)
;; (show-scored-candidates (score-candidates fitness-fn candidates))
;;(use '(incanter core stats charts))
;;(view (function-plot (:fn target) 0 1))