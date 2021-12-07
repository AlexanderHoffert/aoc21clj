(ns advent-of-code.day-07
  (:require [clojure.string :as string]))

(defn parse-input [input]
  (map #(Integer/parseInt %) (string/split input #",")))

(defn calc-consumption [variant value]
  (if (= variant :a)
    value
    (/ (* value (inc value)) 2)))

(defn get-comsumption [variant positions x]
  (for [position positions]
    (->> position
         (- x)
         Math/abs
         (calc-consumption variant))))

(defn get-comsumptions [variant max positions]
  (for [x (range max)]
    (->> x
         (get-comsumption variant positions)
         (apply +))))

(defn calculate-fuel [variant positions]
  (let [max-val (apply max positions)]
    (->> positions
         (get-comsumptions variant max-val)
         (apply min))))

(defn part-1
  "Day 07 Part 1"
  [input]
  (->> input
       parse-input
       (calculate-fuel :a)))

(defn part-2
  "Day 07 Part 2"
  [input]
  (->> input
       parse-input
       (calculate-fuel :b)))
