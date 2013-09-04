(ns com.dollfreaks.bonescript.io.digital
  (:require [cljs.nodejs :as node]
            [com.dollfreaks.bonescript.interop :as iop]
            [cljs.core.async :refer (<!)])
  (:require-macros [cljs.core.async.macros (go)]))

(let [b (node/require "bonescript")]
  (defn write!
    [{pin :pin mode :mode dir :direction :as p} state ch]
    (let [p-name (iop/to-pin-name pin)]
      (if (and (= :digital mode) (= :output dir))
        (do
          ; is there a performance penalty to setting the pin mode every time?
          (iop/set-pin-mode! p-name (iop/to-direction dir))
          (iop/digital-write! p-name state ch))
        ; throw an exception?
        (println "cannot write to an input or non-digital pin") 
        )))

  (defn read 
    [{pin :pin mode :mode dir :direction :as p} ch]

    (let [p-name (iop/to-pin-name pin)]
      (if (and (= :digital mode) (not (= :output dir)))
        (do
          ; is there a performance penalty to setting the pin mode every time?
          (iop/set-pin-mode! p-name (iop/to-direction dir))
          (iop/digital-read p-name ch))
        ; throw an exception?
        (println "cannot read from an output or non-digital pin") 
        )))

  (defn set-high! [p ch] (write! p iop/HIGH ch))
  (defn set-low! [p ch] (write! p iop/LOW ch))
  (defn high? [p ch] (= iop/HIGH (digital-read-from-pin p iop/HIGH)))
  (defn low? [p ch] (digital-read-from-pin p iop/LOW)))
