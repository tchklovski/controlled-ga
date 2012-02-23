(ns controlled-ga.core
  (:use [controlled-ga fitness-scoring mutation]
        [incanter core stats charts]))

(def target-spec
  '(+ (* x x) 0.3))

(def candidate-specs
  (repeat 10
          '(+ (+ (+ x 0)
                 (+ x 1))
              (+ (+ x x)
                 (* x 1)))))

(defn make-individual
  "note that if 'spec' is gibberish, the individual will not be viable -- we return nil for :fn"
  [spec]
  (let [fn-or-nil (try
                    (let [func (eval (list 'fn '[x] spec))]
                      (func 0)
                      func)
                    (catch Exception e
                      nil))]
    {:spec spec
     :fn fn-or-nil}))

(def target (make-individual target-spec))
(def candidates (map make-individual candidate-specs))

(def fitness-fn (make-fitness-fn target))

(defn score-candidates
  "returns candidate hashmaps with :err err-val added; sorts cands by increasing error"
  ([fitness-fn candidates]
      (let [errors (map fitness-fn candidates)
            scored-candidates (map #(assoc %1 :err %2) candidates errors)]
        (sort-by :err scored-candidates)))
  ([candidates] (score-candidates fitness-fn candidates)))

(def show-candidate
  (juxt [:err :spec]))

(defn plot-vs-target [cand]
    (doto (function-plot (:fn target) 0 1)
      (add-function (:fn cand) 0 1)
      view))

(def mutate-candidate
  (comp make-individual mutate :spec))

(defn mutated-candidates
  "an infinite seq of mutations of randomly picked candidates"
  [cands]
  (remove #(nil? (:fn %))
          (repeatedly
           #(mutate-candidate (rand-nth cands)))))

(defn step
  "take one step of evolving candiates -- returns same number of candidates as prev step"
  [cands]
  (let [firstn #(take (count cands) %)]
    (firstn (score-candidates (concat cands
                                      (firstn (mutated-candidates cands)))))))

(defn show-run []
  (let [generations (iterate step candidates)
        show-candidates #(map show-candidate %)
        show-generation (comp show-candidates (partial take 3))
        gens (map show-generation generations)]
  (nth gens 30)))

(comment
  (in-ns 'controlled-ga.core)
  )

