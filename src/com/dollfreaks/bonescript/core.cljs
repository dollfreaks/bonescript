(ns com.dollfreaks.bonescript.core
  (:require [cljs.nodejs :as node]
            [cljs.core.async :refer (chan >! <!)])
  (:require-macros [cljs.core.async.macros :refer (go)]))

(let [b (cljs.nodejs/require "bonescript")
      send-message (fn [ch] (fn [& args] (go (>! ch args))))]

  (defn get-platform 
    ([] (get-platform (chan 1)))
    ([ch]
     (.getPlatform b (send-message ch))
     ch))

  (defn set-pin-mode!
    ([opts] (set-pin-mode! opts (chan 1)))
    ([{pin :pin
       direction :direction
       mux :mux
       pullup :pullup
       slew :slew}
      ch]
     (apply #(.pinMode b %) [pin direction mux pullup slew (send-message ch)])
     ch))

  (defn get-pin-mode
    ([p] (get-pin-mode p (chan 1)))
    ([p ch]
     (.getPinMode b p (send-message ch))
     ch))

  (defn digital-write!
    ([p v] (digital-write! p v (chan 1)))
    ([p v ch]
     (.digitalWrite b p v (send-message ch))
     ch))

  (defn digital-read
    ([p] (digital-read [p (chan 1)]))
    ([p ch]
     (.digitalRead b p (send-message ch))
     ch))


  (defn analog-write!
    ([p v hz] (analog-write! p v hz (chan 1)))
    ([p v hz ch]
     (.analogWrite b p v hz (send-message ch))
     ch))

  (defn analog-read
    ([p] (analog-read [p (chan 1)]))
    ([p ch]
     (.digitalRead b p (send-message ch))
     ch))

  (defn attach-interrupt!
    ([p h m] (attach-interrupt! p h m (chan 1)))
    ([p h m ch]
     (.attachInterrupt b p h m (send-message ch))
     ch))

  (defn detatch-interrupt!
    ([p] (detatch-interrupt! p (chan 1)))
    ([p ch]
     (.detachInterrupt b p (send-message ch))))

  (defn read-text-file
    ([p] (read-text-file p (chan 1)))
    ([p ch]
     (.readTextFile b p (send-message ch))
     ch))

  (defn write-text-file!
    ([p d] (write-text-file! p d (chan 1)))
    ([p d ch]
     (.writeTextFile b p d (send-message ch))
     ch))

  (defn shift-out!
    ([dp cp order v] (shift-out! dp cp order v (chan 1)))
    ([dp cp order v ch]
     (.shiftOut b dp cp order v (send-message ch))
     ch)))

(defn start [& _]
  (go
    (let [ch (chan 1)]
      (println (<! (get-platform ch))))))

(set! *main-cli-fn* start)
