(ns com.dollfreaks.bonescript.io.digital
  (:require [com.dollfreaks.bonescript.interop :as iop]
            [cljs.core.async :refer (<! >! chan close!)])
  (:require-macros [cljs.core.async.macros :refer (go)]))

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

(defn set-high!
  ([p] (set-high! p (chan 1)))
  ([p ch] (write! p iop/HIGH ch)))

(defn set-low!
  ([p] (set-low! p (chan 1)))
  ([p ch] (write! p iop/LOW ch)))

(defn pin-has-value? [p v ch]
  (let [temp-chan (chan 1)]
    (go
      (>! ch (= v (:value (<! (iop/digital-read-from-pin p temp-chan)))))
      (close! temp-chan)) ch))

(defn high? [p ch]
  (pin-has-value? p iop/HIGH ch))

(defn low? [p ch]
  (pin-has-value? p iop/LOW ch))
