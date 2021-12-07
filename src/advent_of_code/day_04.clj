(ns advent-of-code.day-04
  (:require [clojure.string :as string]))

(defn parse-board-line [line]
  (-> line
      string/trim
      (string/split #"\s+")
      (->> (mapv #(Integer/parseInt %)))))

(defn parse-board [board]
  (-> board
      (string/trim)
      (string/split #"\n")
      (->> (mapv parse-board-line))))

(defn parse-input [input]
  (let [[numbers boards] (string/split input #"\n" 2)]
    {:numbers (-> numbers
                  (string/split #",")
                  (->> (mapv #(Integer/parseInt %))))
     :boards (-> boards
                 (string/split #"\n\n")
                 (->> (mapv parse-board)))}))

(defn part-1
  "Day 04 Part 1"
  [input]
  (parse-input input))

(defn part-2
  "Day 04 Part 2"
  [input]
  0)
