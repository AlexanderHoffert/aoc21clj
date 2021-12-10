(ns advent-of-code.day-10
  (:require [clojure.string :as string]))

(def valid-opening-bracket {\) \(
                            \] \[
                            \} \{
                            \> \<})

(def scores {\) 3
             \] 57
             \} 1197
             \> 25137})

(defn get-score [lines]
  (reduce (fn [sum line]
            (+ sum
               (loop [index 0
                      stack '()]
                 (let [current (get line index)
                       is-closing (contains? valid-opening-bracket current)]
                   (if (= index (count line)) 0
                       (if (and is-closing
                                (not= (first stack)
                                      (valid-opening-bracket current)))
                         (scores current)
                         (recur (inc index) (if is-closing (rest stack) (conj stack current)))))))))
          0 lines))

(defn part-1
  "Day 10 Part 1"
  [input]
  (->> input
       string/split-lines
       get-score))

(defn part-2
  "Day 10 Part 2"
  [input]
  input)
