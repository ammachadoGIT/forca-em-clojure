(ns forca.core)

(def starting-life-count 6)

; TODO: read words from external file
(defn get-random-word [] (rand-nth ["ACRES" "ADULT" "ADVICE" "ARRANGEMENT" "ATTEMPT" "AUGUST" "AUTUMN" "BORDER"
                            "BREEZE" "BRICK" "CALM" "CANAL" "CASEY" "CAST" "CHOSE" "CLAWS" "COACH" "CONSTANTLY" "CONTRAST" "COOKIES"
                            "CUSTOMS" "DAMAGE" "DANNY" "DEEPLY" "DEPTH" "DISCUSSION" "DOLL" "DONKEY" "EGYPT" "ELLEN" "ESSENTIAL"
                            "EXCHANGE" "EXIST" "EXPLANATION" "FACING" "FILM" "FINEST" "FIREPLACE" "FLOATING" "FOLKS" "FORT"
                            "GARAGE" "GRABBED" "GRANDMOTHER" "HABIT" "HAPPILY" "HARRY" "HEADING" "HUNTER" "ILLINOIS" "IMAGE"
                            "INDEPENDENT" "INSTANT" "JANUARY" "KIDS" "LABEL" "LEE" "LUNGS" "MANUFACTURING" "MARTIN" "MATHEMATICS"
                            "MELTED" "MEMORY" "MILL" "MISSION" "MONKEY" "MOUNT" "MYSTERIOUS" "NEIGHBORHOOD" "NORWAY" "NUTS"
                            "OCCASIONALLY" "OFFICIAL" "OURSELVES" "PALACE" "PENNSYLVANIA" "PHILADELPHIA" "PLATES" "POETRY"
                            "POLICEMAN" "POSITIVE" "POSSIBLY" "PRACTICAL" "PRIDE" "PROMISED" "RECALL" "RELATIONSHIP"
                            "REMARKABLE" "REQUIRE" "RHYME" "ROCKY" "RUBBED" "RUSH" "SALE" "SATELLITES" "SATISFIED" "SCARED"
                            "SELECTION" "SHAKE" "SHAKING" "SHALLOW" "SHOUT" "SILLY" "SIMPLEST" "SLIGHT" "SLIP" "SLOPE" "SOAP"
                            "SOLAR" "SPECIES" "SPIN" "STIFF" "SWUNG" "TALES" "THUMB" "TOBACCO" "TOY" "TRAP" "TREATED" "TUNE"
                            "UNIVERSITY" "VAPOR" "VESSELS" "WEALTH" "WOLF" "ZOO"]))

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
  (print "Errors: ")
  (doseq [letter (seq errors)]
    (print letter " "))
  (println)
  (println))

(defn game [life-count word hits errors]
  (display-status life-count word hits errors)
  (cond
    (= life-count 0) (lose word)
    (correct-word? word hits) (win)
    :else
    (let [guess (read-guess!)]
      (if (is-hit? guess word)
        (do
          (recur life-count word (conj hits guess) errors))
        (do
          (recur (dec life-count) word hits (conj errors guess)))))))

(defn start []
  (game starting-life-count (get-random-word) #{} #{}))