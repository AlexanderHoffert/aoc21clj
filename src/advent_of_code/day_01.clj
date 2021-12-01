(ns advent-of-code.day-01)
(require '[clojure.string :as str])

(defn parse-int [s]
  (Integer. s))

(defn read-input [input]
  (map parse-int (str/split input #"\n")))

(defn part-1
  "Day 01 Part 1"
  [input]
  (reduce (fn [r [a b]]
            (if (< a b) (inc r) r))
          0
          (partition 2 1 (read-input input))))

(defn part-2
  "Day 01 Part 2"
  [input]
  (reduce (fn [r [v1 _ _ v4]]
            (if (< v1 v4) (inc r) r))
          0
          (partition 4 1 (read-input input))))
