(ns utility.xf-test
  (:require [clojure.test :refer :all]
            [erinite.utility.xf :as xf]))

(def test-data [0 1 :foo 3 true :bar])

(deftest when-test
  (testing "Apply inc only to ints"
    (is (=  [1 2 :foo 4 true :bar]
            (map (xf/when int? inc)
                 test-data)))))

(deftest cond-test
  (testing "Apply inc to ints and name to keywords"
    (is (= [1 2 "foo" 4 true "bar"]
           (map (xf/cond int? inc
                         keyword? name)
                test-data)))))

(deftest fdefault-test
  (let [f (xf/fdefault inc :default)]
    (testing "Apply function to value"
      (is (= 3 (f 2))))

    (testing "Return default value for nil"
      (is (= :default (f nil))))))

(deftest fsome-test
  (testing "Applies function only to nil values"
    (is (= [1 2 nil 4 nil]
           (map (xf/fsome inc)
                [0 1 nil 3 nil])))))

(deftest finargs-test
  (testing "Replaces an argument with data from a sub-path of that argument"
    (is (= [:a :B]
           ((xf/finargs vector [1 :foo]) :a {:foo :B})))))

(deftest fapply-test
  (testing "Returns a funciton that applies its argument to the input function"
    (is (= 10
           ((xf/fapply +) [5 2 3])))))
