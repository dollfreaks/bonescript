(ns com.dollfreaks.bonescript.interop
  (:require [cljs.nodejs :as node]))

(let [b (node/require "bonescript")]
  (defn to-direction [d]
    (case d
      :output (aget b "OUTPUT")
      :input (aget b "INPUT")
      :pullup (aget b "INPUT_PULLUP")))

  (defn to-pin-name [p]
    (name p))
 
  (def HIGH (aget b "HIGH"))
  (def LOW (aget b "LOW"))
  
  )
