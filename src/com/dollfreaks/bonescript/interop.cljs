(ns com.dollfreaks.bonescript.interop
  (:require [cljs.nodejs :as node]
            [cljs.core.async :refer (chan >! <!)])
  (:require-macros [cljs.core.async.macros :refer (go)]))

(let [b (cljs.nodejs/require "bonescript")
      callback-to-chan (fn [ch] (fn [& args] (go (>! ch args))))]

  (defn to-direction [d]
    (case d
      :output (aget b "OUTPUT")
      :input (aget b "INPUT")
      :pullup (aget b "INPUT_PULLUP")))

  (defn to-pin-name [p] (name p))
 
  (def HIGH (aget b "HIGH"))
  (def LOW (aget b "LOW"))

  (defn get-platform 
    [ch]
     (.getPlatform b (callback-to-chan ch))
     ch)

  ; gonna need some work to support the "returns a channel" model
  (defn set-pin-mode!
    [p mode]
      (.pinMode b p mode))

  (defn get-pin-mode
    [p ch]
     (.getPinMode b p (callback-to-chan ch))
     ch)

  (defn digital-write!
    [p v ch]
     (.digitalWrite b p v (callback-to-chan ch))
     ch)

  (defn digital-read
    [p ch]
     (.digitalRead b p (callback-to-chan ch))
     ch)

  (defn analog-write!
    [p v hz ch]
     (.analogWrite b p v hz (callback-to-chan ch))
     ch)

  (defn analog-read
    [p ch]
     (.digitalRead b p (callback-to-chan ch))
     ch)

  (defn attach-interrupt!
    [p h m ch]
     (.attachInterrupt b p h m (callback-to-chan ch))
     ch)

  (defn detatch-interrupt!
    [p ch]
     (.detachInterrupt b p (callback-to-chan ch)))

  (defn read-text-file
    [p ch]
     (.readTextFile b p (callback-to-chan ch))
     ch)

  (defn write-text-file!
    [p d ch]
     (.writeTextFile b p d (callback-to-chan ch))
     ch)

  (defn shift-out!
    [dp cp order v ch]
     (.shiftOut b dp cp order v (callback-to-chan ch))
     ch))
