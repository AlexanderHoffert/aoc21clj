(ns advent-of-code.day-13
  (:require [clojure.string :as string]))

(defn fold-location [line]
  (-> line
      (subs 13)
      Integer/parseInt))

(defn parse-input [input]
  (let [[points folds] (string/split input #"\n\n")]
    {:points (->> points
                  string/split-lines
                  (map (fn [line] (-> line
                                      string/trim
                                      (string/split #",")
                                      (->> (mapv #(Integer/parseInt %))
                                           (zipmap '(\x \y))))))
                  set)
     :folds (->> folds
                 string/split-lines
                 (map #(string/trim %))
                 (map #(hash-map :direction (get % 11)
                                 :location (fold-location %))))}))

(defn fold-value [value at]
  (if (< value at)
    value
    (- at (- value at))))

(defn fold-once [points fold]
  (->> points
       (filter #(not (= (% (fold :direction)) (fold :location))))
       (map #(if (= (fold :direction) \x)
               {\x (fold-value (% \x) (fold :location))
                \y (% \y)}
               {\x (% \x)
                \y (fold-value (% \y) (fold :location))}))
       set))

(defn fold-only-once [{points :points folds :folds}]
  (fold-once points (first folds)))

(defn fold-all [{points :points folds :folds}]
  (reduce #(fold-once %1 %2) points folds))

(defn print-points [points]
  (let [max-x (reduce max (map #(get % \x) points))
        max-y (reduce max (map #(get % \y) points))]
    (->> (for [y (range 0 (inc max-y))]
           (for [x (range 0 (inc max-x))]

             (if (contains? points {\x x \y y})
               "#"
               " ")))
         (map #(string/join "" %))
         (#(doall (map println %))))))

(defn part-1
  "Day 13 Part 1"
  [input]
  (->> input
       parse-input
       fold-only-once
       count))

(defn part-2
  "Day 13 Part 2"
  [input]
  (->> input
       parse-input
       fold-all
       print-points
       ((fn [_] nil))))
