(ns advent-of-code.day-06
  (:require [clojure.string :as string]))

(defn parse-input [input]
  (map #(Integer/parseInt %) (string/split input #",")))

(defn grow-step [school count]
  [(map (fn [fish] (if (zero? fish) 6 (dec fish))) school) count])

(defn add-eights [[school count]]
  (concat school (repeat count 8)))

(defn grow [initial-school]
  (loop [index 0
         school initial-school]
    (if (= index 80)
      school
      (recur (inc index)
             (->> (count (filter zero? school))
                  (grow-step school)
                  (add-eights))))))

(defn part-1
  "Day 06 Part 1"
  [input]
  (->> (parse-input input)
       grow
       count))

(defn part-2
  "Day 06 Part 2"
  [input]
  input)
