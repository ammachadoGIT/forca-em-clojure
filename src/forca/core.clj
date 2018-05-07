(ns forca.core)

(def starting-life-count 6)
(def secret-word "WATERMELON")

(defn lose []
  (println "You lose!"))

(defn win []
  (println "You win!"))

(defn is-hit? [guess word]
  (.contains word guess))

(defn read-guess! []
  (.toUpperCase (read-line)))

(defn missing-letters [word hits]
  (filter (fn [letter] (not (contains? hits (str letter)))) (seq word)))

(defn correct-word? [word hits]
  (empty? (missing-letters word hits)))

(defn display-status [life-count word hits errors]
  (println "life-count:" life-count)
  (doseq [letter (seq word)]
    (if (contains? hits (str letter))
      (print letter " ") (print "_" " ")))
  (println)
  (print "Errors: ")
  (doseq [letter (seq errors)]
    (print letter " "))
  (println)
  (println))

(defn game [life-count word hits errors]
  (display-status life-count word hits errors)
  (cond
    (= life-count 0) (lose)
    (correct-word? word hits) (win)
    :else
    (let [guess (read-guess!)]
      (if (is-hit? guess word)
        (do
          (recur life-count word (conj hits guess) errors))
        (do
          (recur (dec life-count) word hits (conj errors guess)))))))

(defn start []
  (game starting-life-count secret-word #{} #{}))