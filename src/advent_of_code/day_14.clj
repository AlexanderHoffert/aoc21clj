(ns advent-of-code.day-14
  (:require [clojure.string :as string]))

(defn parse-rule [line]
  (-> line
      string/trim
      (string/split #" -> ")))

(defn parse-input [input]
  (let [[template rules] (string/split input #"\n\n")]
    (hash-map
     :template (string/split template #"")
     :rules (->> rules
                 string/split-lines
                 (map parse-rule)
                 (#(zipmap (map first %) (map second %)))))))

(defn apply-rules [rules segments]
  (->> segments
       (map (fn [segment]
              (let [new-char (get rules (string/join segment))]
                (if (nil? new-char)
                  [(first segment)]
                  [(first segment) new-char]))))
       (apply concat)))

(defn apply-rules-10-times [{initial-template :template rules :rules}]
  (loop [count 0
         template initial-template]
    (if (= count 20)
      template
      (recur
       (inc count)
       (->> template
            (partition 2 1 " ")
            (apply-rules rules))))))

(defn part-1
  "Day 14 Part 1"
  [input]
  (time (->> input
             parse-input
             apply-rules-10-times
             frequencies
             (map second)
             (#(- (reduce max %) (reduce min %))))))

(defn part-2
  "Day 14 Part 2"
  [input]
  nil)

(comment
  (let [rules (parse-input "NNCB

CH -> B
HH -> N
CB -> H
NH -> C
HB -> C
HC -> B
HN -> C
NN -> C
BH -> H
NC -> B
NB -> B
BN -> B
BB -> N
BC -> B
CC -> N
CN -> C")] rules))