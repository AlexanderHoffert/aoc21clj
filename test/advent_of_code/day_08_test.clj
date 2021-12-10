(ns advent-of-code.day-08-test
  (:require [clojure.test :refer [deftest testing is]]
            [advent-of-code.day-08 :refer [part-1 part-2]]
            [clojure.java.io :refer [resource]]))

(deftest part1
  (let [expected 26]
    (is (= expected (part-1 (slurp (resource "day-08-example.txt")))))))

(deftest part2
  (let [expected 61229]
    (is (= expected (part-2 (slurp (resource "day-08-example.txt")))))))
