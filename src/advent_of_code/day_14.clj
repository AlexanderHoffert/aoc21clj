(ns advent-of-code.day-14
  (:require [clojure.string :as string]))

(defn parse-rule [line]
  (-> line
      string/trim
      (string/split #" -> ")))

(defn parse-input [input]
  (let [[template rules] (string/split input #"\n\n")]
    (hash-map
     :pairs (-> template
                (->> (partition 2 1 " ") ; add padding to remain the last char
                     (map string/join))
                frequencies)
     :rules (->> rules
                 string/split-lines
                 (map parse-rule)
                 (#(zipmap (map first %) (map second %)))))))

(defn apply-rules [rules pairs]
  (->> pairs
       (map (fn [pair]
              (let [[chars count] pair
                    new-char (get rules chars)]
                (if (nil? new-char)
                  (reduce hash-map pair)
                  (let [w1 (str (first chars) new-char)
                        w2 (str new-char (second chars))]
                    (if (= w1 w2)
                      {w1 (* count 2)}
                      {w1 count w2 count}))))))
       (apply merge-with +)))

(defn apply-rules-times [times {initial-pairs :pairs rules :rules}]
  (loop [count 0
         pairs initial-pairs]
    (if (= count times)
      pairs
      (recur
       (inc count)
       (apply-rules rules pairs)))))

(defn sum-up-single-chars [char-pair-map]
  (reduce
   (fn [sums pair]
     (let [char (first (first pair))
           old-count (or (get sums char) 0)]
       (assoc sums char (+ old-count (second pair)))))
   {}
   char-pair-map))

(defn calc-result [input times]
  (->> input
       parse-input
       (apply-rules-times times)
       sum-up-single-chars
       (map second)
       (#(- (reduce max %) (reduce min %)))))

(defn part-1
  "Day 14 Part 1"
  [input]
  (calc-result input 10))

(defn part-2
  "Day 14 Part 2"
  [input]
  (time (calc-result input 40)))
