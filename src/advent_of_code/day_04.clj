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

(defn transpose [board]
  (apply map vector board))

(defn get-rows-score [pick-list board]
  (let [numbers (set pick-list)]
    (when (some #(every? numbers %) board)
      (->> board
           (apply concat)
           (remove numbers)
           (apply +)
           (* (last pick-list))))))

(defn get-board-score [pick-list board]
  (or (get-rows-score pick-list board)
      (get-rows-score pick-list (transpose board))))

(defn get-score [pick-list boards]
  (some #(get-board-score pick-list %) boards))

(defn part-1
  "Day 04 Part 1"
  [input]
  (let [{:keys [numbers boards]} (parse-input input)]
    (loop [n 1]
      (let [pick-list (take n numbers)]
        (or (get-score pick-list boards)
            (recur (inc n)))))))

(defn part-2
  "Day 04 Part 2"
  [input]
  0)
