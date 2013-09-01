(ns com.dollfreaks.bonescript.core
  (:require [cljs.nodejs :as node]
            [cljs.core.async :refer (chan >!! <!!)]))

(let [b (cljs.nodejs/require "bonescript")]
  (defn get-platform 
    ([] (get-platform (chan)))
    ([ch]
     (.getPlatform b #(>!! ch %))
     ch))

  (defn set-pin-mode!
    ([opts] (set-pin-mode! opts (chan)))
    ([{pin :pin
       direction :direction
       mux :mux
       pullup :pullup
       slew :slew}
      ch]
     (apply #(.pinMode b %) [pin direction mux pullup slew #(>!! ch %)])
     ch))

  (defn get-pin-mode
    ([p] (get-pin-mode p (chan)))
    ([p ch]
     (.getPinMode b p #(>!! ch %))
     ch))

  (defn digital-write!
    ([p v] (digital-write! p v (chan)))
    ([p v ch]
     (.digitalWrite b p v #(>!! ch %))
     ch))

  (defn digital-read
    ([p] (digital-read [p (chan)]))
    ([p ch]
     (.digitalRead b p #(>!! ch %))
     ch))


  (defn analog-write!
    ([p v hz] (analog-write! p v hz (chan)))
    ([p v hz ch]
     (.analogWrite b p v hz #(>!! ch %))
     ch))

  (defn analog-read
    ([p] (analog-read [p (chan)]))
    ([p ch]
     (.digitalRead b p #(>!! ch %))
     ch))

  (defn attach-interrupt!
    ([p h m] (attach-interrupt! p h m (chan)))
    ([p h m ch]
     (.attachInterrupt b p h m #(>!! ch %))
     ch))

  (defn detatch-interrupt!
    ([p] (detatch-interrupt! p (chan)))
    ([p ch]
     (.detachInterrupt b p #(>!! ch %))))

  (defn read-text-file
    ([p] (read-text-file p (chan)))
    ([p ch]
     (.readTextFile b p #(>!! ch %))
     ch))

  (defn write-text-file!
    ([p d] (write-text-file! p d (chan)))
    ([p d ch]
     (.writeTextFile b p d #(>!! ch %))
     ch))

  (defn shift-out!
    ([dp cp order v] (shift-out! dp cp order v (chan)))
    ([dp cp order v ch]
     (.shiftOut b dp cp order v #(>!! ch %))
     ch)))

(defn start [& _]
  (println (<!! (get-platform (chan)))))

(set! *main-cli-fn* start)
