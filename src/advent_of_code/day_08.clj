(ns advent-of-code.day-08
  (:require [clojure.string :as string]))

(defn parse-segment [segment]
  (-> segment
      (string/trim)
      (string/split #"\s+")))

(defn parse-line [line]
  (->> (string/split line #"\|")
       (mapv parse-segment)
       (zipmap [:signals :output])))

(defn parse-input [input]
  (->> (string/split input #"\n")
       (mapv parse-line)))

(def easy-values #{2 3 4 7})

(defn count-output-digits [output]
  (count (filter #(contains? easy-values (count %)) output)))

(defn count-digits [lines]
  (reduce (fn [acc line] (+ acc (count-output-digits (line :output))))
          0 lines))

(defn part-1
  "Day 08 Part 1"
  [input]
  (->> (parse-input input)
       (count-digits)))

(defn part-2
  "Day 08 Part 2"
  [input]
  input)

(comment
  (parse-segment "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb")
  (parse-line "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe"))