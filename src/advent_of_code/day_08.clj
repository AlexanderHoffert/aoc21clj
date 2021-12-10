(ns advent-of-code.day-08
  (:require [clojure.string :as string]
            [clojure.set :refer [difference subset?]]))

(defn parse-segment [segment]
  (-> segment
      (string/trim)
      (string/split #"\s+")
      (->> (mapv set))))

(defn parse-line [line]
  (->> (string/split line #"\|")
       (mapv parse-segment)
       (zipmap [:signals :output])))

(defn parse-input [input]
  (->> (string/split input #"\n")
       (mapv parse-line)))

(def easy-values #{2 3 4 7})

(defn count-output-digits [output]
  (count (filter #(contains? easy-values (count %)) output)))

(defn count-digits [lines]
  (reduce (fn [acc line] (+ acc (count-output-digits (line :output))))
          0 lines))

(defn part-1
  "Day 08 Part 1"
  [input]
  (->> (parse-input input)
       (count-digits)))

(defn get-counts [signals]
  (zipmap (range 2 8) (for [c (range 2 8)] (set (filter #(= (count %) c) signals)))))

(defn get-numbers [counts]
  (let [one (first (counts 2))
        four (first (counts 4))
        seven (first (counts 3))
        eight (first (counts 7))
        six (->> (counts 6)
                 (some #(when (not (subset? one %)) %)))
        three (->> (counts 5)
                   (some #(when (subset? one %) %)))
        nine (->> (counts 6)
                  (some #(when (subset? three %) %)))
        zero (->> #{six nine}
                  (difference (counts 6))
                  first)
        five (->> (counts 5)
                  (some #(when (subset? % six) %)))
        two (->> #{three five}
                 (difference (counts 5))
                 first)]
    {one 1 two 2 three 3 four 4 five 5 six 6 seven 7 eight 8 nine 9 zero 0}))

(defn get-output-value
  [line]
  (->> (line :signals)
       get-counts
       get-numbers
       (#(map % (line :output)))
       string/join
       Integer/parseInt))

(defn get-output-values [lines]
  (apply + (map get-output-value lines)))

(defn part-2
  "Day 08 Part 2"
  [input]
  (->> input
       parse-input
       get-output-values))

(comment
  (parse-segment "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb")
  (parse-line "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe")
  (let [line (parse-line "fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg")
        signals (:signals line)
        counts (get-counts signals)
        numbers (get-numbers counts)]
    numbers))
