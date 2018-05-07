(ns forca.core)

(declare check-guess)

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
	(filter (fn [letra] (not (contains? hits (str letra)))) (seq word)))

(defn correct-word? [word hits]
	(empty? (missing-letters word hits)))

(defn game [life-count word hits]
	 (cond
        (= life-count 0) (lose)
        (correct-word? word hits) (win)
        :else   
        (let [guess (read-guess!)]
            (if (is-hit? guess word)
                (do
                    (recur life-count word (conj hits guess)))
                (do
					(recur (dec life-count) word hits))))))

(defn check-guess [guess life-count word hits]
	(if (is-hit? guess word) 
		(game life-count word (conj hits guess))
		(game (dec life-count) word hits)))

(defn start []
	(game starting-life-count secret-word #{}))