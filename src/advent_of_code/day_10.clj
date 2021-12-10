(ns advent-of-code.day-10
  (:require [clojure.string :as string]))

(def valid-opening-bracket {\) \(
                            \] \[
                            \} \{
                            \> \<})

(def scores {\) 3
             \] 57
             \} 1197
             \> 25137

             \( 1
             \[ 2
             \{ 3
             \< 4})

(defn get-line-score [end-score-fn corrupted-score-fn line]
  (loop [index 0
         stack '()]
    (let [current (get line index)
          is-closing (contains? valid-opening-bracket current)]
      (if (= index (count line)) (end-score-fn stack)
          (if (and is-closing
                   (not= (first stack)
                         (valid-opening-bracket current)))
            (corrupted-score-fn current)
            (recur (inc index) (if is-closing (rest stack) (conj stack current))))))))

(defn get-incomplete-stack-score [stack]
  (reduce (fn [acc bracket]
            (->> acc
                 (* 5)
                 (+ (scores bracket))))
          0 stack))

(defn get-corruption-score [lines]
  (->> lines
       (map (partial get-line-score (fn [_] 0) #(scores %)))
       (apply +)))

(defn get-incomplete-score [lines]
  (->> lines
       (map (partial get-line-score get-incomplete-stack-score (fn [_] 0)))
       (filter (fn [value] (not (zero? value))))
       sort
       ((fn [values] (nth values (/ (count values) 2))))))

(defn part-1
  "Day 10 Part 1"
  [input]
  (->> input
       string/split-lines
       get-corruption-score))

(defn part-2
  "Day 10 Part 2"
  [input]
  (->> input
       string/split-lines
       get-incomplete-score))
