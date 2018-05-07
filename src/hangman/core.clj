(ns hangman.core)
(use 'clojure.java.io)

(def starting-life-count 6)

(defn get-words-from-file [fname]
  (with-open [r (reader fname)]
    (doall (line-seq r))))

(def random-words
  (get-words-from-file "src/hangman/words.txt"))

(defn get-random-word []
  (rand-nth random-words))

(defn lose [word]
  (println "You lose!")
  (println "The secret word was" word))

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
  (print (str (char 27) "[2J"))
  (println "life-count:" life-count)
  (doseq [letter (seq word)]
    (if (contains? hits (str letter))
      (print letter " ") (print "_" " ")))
  (println)
  (println)
  (if (> (count errors) 0)
    (do
      (print "Errors: ")
      (doseq [letter (seq errors)]
        (print letter " "))))
  (println)
  (println))

(defn game [life-count word hits errors]
  (display-status life-count word hits errors)
  (cond
    (= life-count 0) (lose word)
    (correct-word? word hits) (win)
    :else
    (let [guess (read-guess!)]
      (if (contains? errors guess)
        (recur life-count word hits errors)
        (if (is-hit? guess word)
          (recur life-count word (conj hits guess) errors)
          (recur (dec life-count) word hits (conj errors guess)))))))

(defn -main
  "A fun Hangman game."
  [& args]
  (game starting-life-count (get-random-word) #{} #{}))
