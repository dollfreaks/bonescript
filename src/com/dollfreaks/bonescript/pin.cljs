(ns com.dollfreaks.bonescript.pin)

(defn pin ([k] (pin {} k))
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
