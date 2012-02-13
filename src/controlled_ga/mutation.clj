(ns controlled-ga.mutation
  (:require [clojure.zip :as zip]))

;; this is mutation in evolutionary sense, not "mutable data structure"

;; we take the approach that a given mutation is an application of a
;; template (eg (+ x 1) to some expression x. eg if expr is 2, then
;; the template instantiates to (+ 2 1)

;; we take advantage of the following observations:
;; - functions of one variable are also interpretable as templates
;; of one argument
;; - rather than have a library (list) of eg templates, we can simply have
;; a larger tree of code and select nodes or leaves in it as templates
;; - we don't have to worry about generating valid code right off the bat;
;; we can just discard things that can't compute (Eg (3 4 5))

(defn expr-evals-ok?
  "returns true unless '(eval expr)' throws an Exception; as such,
   does not catch infinite loops"
  [expr]
  (try
    (eval expr)
    true
    (catch Exception e
      false)))

(def initial-expr
  "an expression for us to start mutation with"
  '(+ (+ (+ x 0)
         (+ x 1))
      (+ (+ x x)
         (* x 1))))


(defn make-probability-tester
  "returns a fn that returns true with probability p, false with 1-p"
  [p]
  (fn [] (<= (rand) p)))

(defn zip-apply
  "zip-world and back: turn seq into zip, apply zipfn that returns a loc and go back"
  [zipfn]
  (comp zip/node zipfn zip/seq-zip))

(defn zip-rand-nth
  "rand-nth of children of loc; loc must be zip/branch?"
  [loc]
  (let [rand-child-pos (rand (count (zip/children loc)))]
    (nth (iterate zip/right (zip/down loc)) rand-child-pos)))

(defn zip-pick-node
  "pick and return a random node (not necessarily leaf) in a tree
   try it with eg (dotimes [_ 10] (prn (zip-apply zip-pick-node initial-expr)))"
  [loc]
  (let [continue? (make-probability-tester 0.7)]
    (if (and (zip/branch? loc) (continue?))
      (recur (zip-rand-nth loc))
      loc)))

(defn zip-mutate [loc]
  (let [mut-loc (zip-pick-node loc)
        mut-val (zip/node (zip-pick-node loc))]
    (zip/root (zip/replace mut-loc mut-val))))

(def mutate
  "stochastic mutation of the expression passed in"
  (comp zip/root zip-mutate zip/seq-zip))


;; visit aspects of the expression, mutating those

;; can mutate just one thing per generation (or zero)

;; mutator machinery vs. an actual (evolvable) mutator

(defn mutate-expression-aux
  [expr]
  1)

(defmulti mutate class)
(defmethod mutate
    Number [n]
    (+ n 1))

