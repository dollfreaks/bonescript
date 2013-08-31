(ns com.dollfreaks.bonescript.core
  (:require [cljs.core.async :refer (chan >!!)]
            [clsj.nodejs :as node]))

(def b (node/require "bonescript"))

(defn get-platform 
  ([] (get-platform (chan)))
  ([ch]
   (b/getPlatform #(>!! ch %))
   ch))

(defn set-pin-mode!
  ([opts] (pin-mode opts (chan)))
  ([{pin :pin
     direction :direction
     mux :mux
     pullup :pullup
     slew :slew}
    ch]
   (apply b/pinMode [pin direction mux pullup slew #(>!! ch %)])
   ch))

(defn get-pin-mode
  ([p] (get-pin-mode p (chan)))
  ([p ch]
   (b/getPinMode p #(>!! ch %))
   ch))

(defn digital-write!
  ([p v] (digital-write! p v (chan)))
  ([p v ch]
   (b/digitalWrite p v #(>!! ch %))
   ch))

(defn digital-read
  ([p] (digital-read [p (chan)]))
  ([p ch]
   (b/digitalRead p #(>!! ch %))
   ch))


(defn analog-write!
  ([p v hz] (analog-write! p v hz (chan)))
  ([p v hz ch]
   (b/analogWrite p v hz #(>!! ch %))
   ch))

(defn analog-read
  ([p] (analog-read [p (chan)]))
  ([p ch]
   (b/digitalRead p #(>!! ch %))
   ch))

(defn attach-interrupt!
  ([p h m] (attach-interrupt p h m (chan)))
  ([p h m ch]
   (b/attachInterrupt p h m #(>!! ch %))
   ch))

(defn detach-interrupt!
  ([p] (deftach-interrupt! p (chan)))
  ([p ch]
   (b/detachInterrupt p #(>!! ch %))))

(defn read-text-file
  ([p] (read-file p (chan)))
  ([p ch]
   (b/readTextFile p #(>!! ch %))
   ch))

(defn write-text-file!
  ([p d] (write-file p d (chan)))
  ([p d ch]
   (b/writeTextFile p d #(>!! ch %))
   ch))

(defn shift-out!
  ([dp cp order v] (shift-out dp cp order v (chan)))
  ([dp cp order v ch]
   (b/shiftOut dp cp order v #(>!! ch %))
   ch))


