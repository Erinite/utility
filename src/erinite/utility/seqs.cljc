(ns erinite.utility.seqs)

(defn index-by
  "Returns a map of the elements of coll keyed by the result of
  f on each element. The value at each key will be the last value
  matching the key as appeared in coll."
  [f coll]
  (persistent!
   (reduce
    (fn [ret x]
      (assoc! ret (f x) x))
    (transient {}) coll)))
