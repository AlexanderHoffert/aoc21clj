(ns advent-of-code.day-09
  (:require [clojure.string :as string]))

(defn parse-line [line]
  (->> (vec line)
       (mapv #(Character/digit % 10))))

(defn parse-input [input]
  (->> (string/split input #"\n")
       (mapv parse-line)))

(defn pad-vector [padding vector]
  (conj (into [padding] vector) padding))

(defn padded-partition [padding vector]
  (->> vector
       (pad-vector padding)
       (partition 3 1)))

(defn is-low [line-triple index]
  (let [center-line (nth line-triple 1)
        center (nth center-line index)]
    (if (and (< center (nth center-line (dec index)))
             (< center (nth center-line (inc index)))
             (< center (nth (nth line-triple 0) index))
             (< center (nth (nth line-triple 2) index)))
      (inc center) 0)))

(defn count-low-points [lines]
  (let [line-length (count (first lines))
        lines-inner-padded (map #(pad-vector 10 %) lines)
        padding-line (pad-vector 10 (repeat line-length 10))
        lines-padded (padded-partition padding-line lines-inner-padded)]
    (reduce (fn [total line-triple]
              (+ total
                 (->> (for [index (range 1 (inc line-length))]
                        (is-low line-triple index))
                      (apply +))))
            0 lines-padded)))

(defn part-1
  "Day 09 Part 1"
  [input]
  (->> input
       parse-input
       count-low-points))

(defn part-2
  "Day 09 Part 2"
  [input]
  nil)

(comment
  (parse-input "2199943210
3987894921
9856789892
8767896789
9899965678")
  (padded-partition [2 3 4] 9)
  (padded-partition [[1 2 3] [4 5 6]] [9 9 9])
  (is-low '((10 2 1) (2 1 9) (1 9 9) (9 9 9) (9 9 4) (9 4 3) (4 3 2) (3 2 1) (2 1 0) (1 0 10)) 1)
  (nth '((1 2 3) (3 2 1) (4 3 1)) 1))