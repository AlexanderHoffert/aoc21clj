(ns advent-of-code.day-01)
(require '[clojure.string :as string])

(defn read-input [input]
  (map #(Integer. %) (string/split input #"\n")))

(defn count-increases
  "Counts the number of number pairs where the first element is lower"
  [input]
  (reduce (fn [acc [a b]]
            (if (< a b) (inc acc) acc))
          0
          input))

(defn part-1
  "Day 01 Part 1"
  [input]
  (count-increases (partition 2 1 (read-input input))))

(defn part-2
  "Day 01 Part 2"
  [input]
  (count-increases
   (map
    (fn [[a _ _ b]] (list a b))
    (partition 4 1 (read-input input)))))
