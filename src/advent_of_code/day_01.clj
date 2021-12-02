(ns advent-of-code.day-01)
(require '[clojure.string :as string])

(defn read-input [input]
  (map #(Integer. %) (string/split input #"\n")))

(defn count-increases
  "Counts the number of number pairs where the first element is lower"
  [pairs]
  (reduce (fn [acc [a b]]
            (if (< a b) (inc acc) acc))
          0
          pairs))

(defn count-increases-2
  "Counts the number of number pairs where the first element is lower
   Does exactly the same as count-increases"
  [pairs]
  (count (filter #(apply < %) pairs)))

(defn part-1
  "Day 01 Part 1"
  [input]
  (->> input
       read-input
       (partition 2 1)
       count-increases))

(defn part-2
  "Day 01 Part 2"
  [input]
  (->> input
       read-input
       (partition 4 1)
       (map (fn [[a _ _ b]] (list a b)))
       count-increases-2))
