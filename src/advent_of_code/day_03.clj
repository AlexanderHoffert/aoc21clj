(ns advent-of-code.day-03)
(require '[clojure.string :as string])

(defn read-input [input]
  (string/split input #"\n"))

(defn count-1-bits [lines]
  (reduce (fn [sums line]
            (map-indexed (fn [index sum]
                           (+ sum
                              (Character/digit (nth line index) 2)))
                         sums))
          (repeat (count (first lines)) 0)
          lines))

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

(comment "+++here begins part 2+++")

(defn more-1-bits
  "true if there are more 1 than 0 as nth char of each line"
  [lines index]
  (>= (reduce (fn [count line] (+ count (Character/digit (nth line index) 2)))
              0
              lines)
      (/ (count lines) 2)))

(defn get-accepted-value [oxygen has-more-1-bits]
  (if (or (and oxygen has-more-1-bits)
          (and (not oxygen) (not has-more-1-bits)))
    \1
    \0))

(defn filter-lines [lines index oxygen has-more-1-bits]
  (filter #(=
            (nth % index)
            (get-accepted-value oxygen has-more-1-bits))
          lines))

(defn find-rating [lines oxygen]
  (loop [index 0
         remaining-lines lines]
    (if (< (count remaining-lines) 2)
      (first remaining-lines)
      (recur
       (inc index)
       (filter-lines remaining-lines index oxygen (more-1-bits remaining-lines index))))))

(defn find-rating-value [lines oxygen]
  (Integer/parseInt (find-rating lines oxygen) 2))

(defn calc-life-support-rating [lines]
  (* (find-rating-value lines true) (find-rating-value lines false)))

(defn part-2
  "Day 03 Part 2"
  [input]
  (->> input
       read-input
       calc-life-support-rating))
