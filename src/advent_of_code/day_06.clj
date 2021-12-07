(ns advent-of-code.day-06
  (:require [clojure.string :as string]))

(defn count-fishs [integers]
  (vec (let [counts (frequencies integers)]
         (for [x (range 9)]
           (or (counts x) 0)))))

(defn parse-input [input]
  (->> (map #(Integer/parseInt %) (string/split input #","))
       count-fishs))

(defn grow [days initial-school]
  (loop [index 0
         school initial-school]
    (if (= index days)
      school
      (recur
       (inc index)
       (let [zeros (school 0)
             sevens (school 7)]
         (-> school
             (subvec 1)
             (assoc 6 (+ sevens zeros))
             (conj zeros)))))))

(defn part-1
  "Day 06 Part 1"
  [input]
  (->> (parse-input input)
       (grow 80)
       (apply +)))

(defn part-2
  "Day 06 Part 2"
  [input]
  (->> (parse-input input)
       (grow 256)
       (apply +)))

(comment
  (parse-input "3,4,3,1,2"))