(ns advent-of-code.day-02)
(require '[clojure.string :as string])

(defn parse-int [str] (#(Integer. str)))

(defn parse-line [line]
  ((fn [[direction amount]] [direction (parse-int amount)])
   (string/split line #" ")))

(defn read-input [input]
  (map parse-line (string/split input #"\n")))

(defn get-position [input]
  (reduce
   (fn [[x y] [direction amount]]
     (cond
       (= direction "up") [x (- y amount)]
       (= direction "down") [x (+ y amount)]
       :else [(+ x amount) y]))
   [0 0]
   (read-input input)))

(defn get-position-with-aim [input]
  (reduce
   (fn [[x y aim] [direction amount]]
     (cond
       (= direction "up") [x y (- aim amount)]
       (= direction "down") [x y (+ aim amount)]
       :else [(+ x amount) (+ y (* aim amount)) aim]))
   [0 0 0]
   (read-input input)))

(defn part-1
  "Day 02 Part 1"
  [input]
  (#(* (first %) (second %)) (get-position input)))

(defn part-2
  "Day 02 Part 2"
  [input]
  (#(* (first %) (second %)) (get-position-with-aim input)))
