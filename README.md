# utility
A swiss-army-knife of utility functions


# Reference

## erinite.utility.xf

```clojure
(require '[erinite.utility.xf :as xf])
```

### when

`(fn [condition xform])`

Returns a transformation function that applies `xform` to input if applying `condition` is truthy, otherwise returns input unchanged.

```clojure
(map (xf/when int? inc) [0 1 :foo 3 true :bar])
```
⇒
```clojure
[1 2 :foo 4 true :bar]
```

### cond

`(fn [condition xform ...])`

Returns a transformation function that applies `xform` to input if applying `condition` is truthy. Attempts each `condition`/`xform` pair in turn until `condition` returns truthy or no pairs remain, in which case input is returned unchanged.

```clojure
(map (xf/cond int? inc keyword? name) [0 1 :foo 3 true :bar])
```
⇒
```clojure
[1 2 "foo" 4 true "bar"]
```

### fdefault

`(fn [f v])`

Returns a function which applies `f` only if argument is not nil, otherwise returns `v`.

Similar to `fnil`:
 * `fnil` replaces nil argument with `v` (and subsequent values, if provided)
 * `fdefault` replaces the call to `f` with `v` if input is nil.

```clojure
(let [fnil-test (fnil inc 0)
      fdefault-test (xf/fdefault inc :default)]
  [[(fnil-test 2) (fnil-test nil)]
   [(fdefault-test 2) (fdefault-test nil)]])
```
⇒
```clojure
[[3 1] [3 :default]]
```

### fsome

`(fn [f])`

Apply `f` if the argument is not nil.


```clojure
(map (xf/fsome inc) [0 1 nil 3 nil])
```
⇒
```clojure
[1 2 nil 4 nil]
```

