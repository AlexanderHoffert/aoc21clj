(ns advent-of-code.day-14-test
  (:require [clojure.test :refer [deftest testing is]]
            [advent-of-code.day-14 :refer [part-1 part-2]]
            [clojure.java.io :refer [resource]]))

(deftest part1
  (let [expected 1588]
    (is (= expected (part-1 (slurp (resource "day-14-example.txt")))))))

(deftest part2
  (let [expected 2188189693529]
    (is (= expected (part-2 (slurp (resource "day-14-example.txt")))))))
