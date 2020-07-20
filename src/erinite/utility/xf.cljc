(ns erinite.utility.xf
  (:refer-clojure :rename {cond core-cond when core-when})
  (:require [clojure.core]))

(defn when
  "Returns function which takes an argument `value` and applies `xform` if `(condition value)` is truthy, otherwise returns `value` unchanged."
  [condition xform]
  (fn [value]
    (if (condition value)
      (xform value)
      value))) 
  
(defmacro cond
  "Returns a transformation function that applies xform to input if applying condition is truthy.
  Attempts each condition/xform pair in turn until condition returns truthy or no pairs remain, in which case input is returned unchanged."
  [& clauses]
  (core-when (odd? (count clauses))
    (throw (IllegalArgumentException. "cond requires an even number of forms")))
  (let [value (gensym)
        clauses (mapcat
                  (fn [[condition xform]]
                    `((~condition ~value) (~xform ~value)))
                  (partition 2 clauses))]
    `(fn [~value]
       (core-cond
         ~@clauses
         :else ~value))))

(defn fdefault
  "Returns a function which applies f only if argument is not nil, otherwise returns v."
  [f v]
  (fn [value]
    (if value 
      (f value)
      v)))

(defn fsome
  "Apply f if the argument is not nil."
  [f]
  (fn [value]
    (core-when value (f value))))

