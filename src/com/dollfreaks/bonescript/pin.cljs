(ns com.dollfreaks.bonescript.pin
  (:require [cljs.nodejs :as node]
            [com.dollfreaks.bonescript.core :as core]
            [com.dollfreaks.bonescript.interop :as iop]))

(let [b (node/require "bonescript")]
  (defn pin 
    ([k] (pin {} k))
    ([p k] (assoc p :pin k)))

  (defn output
    ([] (output {}))
    ([p] (assoc p :direction :output)))

  (defn input
    ([] (input {}))
    ([p] (assoc p :direction :input)))

  (defn pullup
    ([] (pullup {}))
    ([p] (assoc p :direction :pullup)))

  (defn digital
    ([] (digital {}))
    ([p] (assoc p :mode :digital)))

  (defn analog
    ([] (analog {}))
    ([p] (assoc p :mode :analog)))

  (defn- digital-write-to-pin [{pin :pin mode :mode dir :direction :as p} state]
    (let [p-name (iop/to-pin-name pin)]
      (if (and (= :digital mode) (= :output dir))
        (do
          ; is there a performance penalty to setting the pin mode every time?
          (core/set-pin-mode! p-name (iop/to-direction dir))
          (core/digital-write! p-name state))
        ; throw an exception?
        (println "cannot write to an input or non-digital pin") 
        )))

  (defn set-high! [p] (digital-write-to-pin p iop/HIGH))
  (defn set-low! [p] (digital-write-to-pin p iop/LOW))

  (defn- digital-read-from-pin [{pin :pin mode :mode dir :direction :as p} state]
    (let [p-name (iop/to-pin-name pin)]
      (if (and (= :digital mode) (not (= :output dir)))
        (do
          ; is there a performance penalty to setting the pin mode every time?
          (core/set-pin-mode! p-name (iop/to-direction dir))
          (= state (core/digital-read p-name)))
        ; throw an exception?
        (println "cannot read from an output or non-digital pin") 
        )))

  (defn high? [p] (digital-read-from-pin p iop/HIGH))
  (defn low? [p] (digital-read-from-pin p iop/LOW))

)
