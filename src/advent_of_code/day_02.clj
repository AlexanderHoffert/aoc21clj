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
   [0 0] input))

(defn get-position-using-aim [input]
  (pop
   (reduce (fn [[x y aim] [direction amount]]
             (cond
               (= direction "up") [x y (- aim amount)]
               (= direction "down") [x y (+ aim amount)]
               :else [(+ x amount) (+ y (* aim amount)) aim]))
           [0 0 0]
           input)))

(defn part-1
  "Day 02 Part 1"
  [input]
  (apply * (get-position (read-input input))))

(defn part-2
  "Day 02 Part 2"
  [input]
  (apply * (get-position-using-aim (read-input input))))
