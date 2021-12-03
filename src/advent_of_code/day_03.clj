(ns advent-of-code.day-03)
(require '[clojure.string :as string])

(defn read-input [input]
  (string/split input #"\n"))

(defn count-1-bits [input]
  (reduce (fn [sums line]
            (map-indexed (fn [index sum]
                           (+ sum
                              (Character/digit (nth line index) 2)))
                         sums))
          (repeat (count (first input)) 0)
          input))

(defn get-number [half gamma counts]
  (Integer/parseInt
   (String/join "" (map #(if (or
                              (and gamma (> % half))
                              (and (not gamma) (< % half)))
                           "1" "0")
                        counts))
   2))

(defn calc [half counts]
  (*
   (get-number half true counts)
   (get-number half false counts)))

(defn part-1
  "Day 03 Part 1"
  [input]
  (let [lines (read-input input)]
    (->> lines
         count-1-bits
         (calc (/ (count lines) 2)))))

(defn part-2
  "Day 03 Part 2"
  [input]
  0)
