(ns advent-of-code.day-05
  (:require [clojure.string :as string]))

(defrecord Vent [x1 x2 y1 y2 notdiagonal])

(defn parse-line [line]
  (let [[x1 y1 x2 y2] (->> (re-matches #"(\d+),(\d+) -> (\d+),(\d+)" line)
                           (next)
                           (mapv #(Long/parseLong %)))]
    (Vent. (min x1 x2) (max x1 x2) (min y1 y2) (max y1 y2) (or (= x1 x2) (= y1 y2)))))

(defn parse-input [input]
  (map parse-line (string/split input #"\n")))

(defn filter-out-diagonal [vents]
  (filter (fn [vent] (:notdiagonal vent)) vents))

(defn vent-contains [x y vent]
  (and
   (>= x (:x1 vent))
   (<= x (:x2 vent))
   (>= y (:y1 vent))
   (<= y (:y2 vent))))

(defn vent-diagonal-constrain [x y vent]
  (or
   (:notdiagonal vent)
   (= (- x (:x1 vent))
      (- y (:y1 vent)))))

(defn is-duplicated [vents x y]
  (> (reduce (fn [acc vent]
               (if
                (and (vent-contains x y vent)
                     (vent-diagonal-constrain x y vent))
                 (inc acc)
                 acc))
             0
             vents)
     1))

(defn get-duplicate-counts [vents width height]
  (->> (for [x (range width)
             y (range height)] (is-duplicated vents x y))
       (filter true?)
       count))

(defn part-1
  "Day 05 Part 1"
  [input]
  (let [vents  (filter-out-diagonal (parse-input input))
        width  (inc (reduce max (map :x2 vents)))
        height (inc (reduce max (map :y2 vents)))]
    (get-duplicate-counts vents width height)))

(defn part-2
  "Day 05 Part 2"
  [input]
  (let [vents  (parse-input input)
        width  (inc (reduce max (map :x2 vents)))
        height (inc (reduce max (map :y2 vents)))]
    (get-duplicate-counts vents width height)))

(comment
  (parse-input "0,9 -> 5,9
8,0 -> 0,8
9,4 -> 3,4
2,2 -> 2,1
7,0 -> 7,4
6,4 -> 2,0
0,9 -> 2,9
3,4 -> 1,4
0,0 -> 8,8
5,5 -> 8,2
"))