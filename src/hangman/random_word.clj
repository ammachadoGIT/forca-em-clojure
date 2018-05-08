(ns hangman.random-word)

(use 'clojure.java.io)

(defn get-words-from-file [fname]
  (with-open [r (reader fname)]
    (doall (line-seq r))))

(defn get-random-word []
  (rand-nth (get-words-from-file "data/words.txt")))
