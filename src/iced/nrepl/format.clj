(ns iced.nrepl.format
  (:require [cljfmt.core :as fmt]))

(defn- ensure-symbol [x]
  (symbol
   (cond
     (keyword? x) (subs (str x) 1)
     :else x)))

(defn- gen-option* [user-indents]
  {:indents (reduce (fn [res [k v]]
              (assoc res (ensure-symbol k) (read-string v)))
                    fmt/default-indents user-indents)})

(def ^:private gen-option
  (memoize gen-option*))

(defn code [code-str indents]
  (let [option (gen-option indents)]
    (fmt/reformat-string code-str option)))


