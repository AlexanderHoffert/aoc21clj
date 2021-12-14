(ns advent-of-code.day-11
  (:require [clojure.string :as string]))

(defn parse-input [input]
  (->> input
       string/split-lines
       (map vec)))

(defn part-1
  "Day 11 Part 1"
  [input]
  (->> input
       parse-input))

(defn part-2
  "Day 11 Part 2"
  [input]
  input)
