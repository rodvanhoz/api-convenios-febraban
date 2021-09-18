(ns api-convenios-febraban.utils.adapters
  (:require [cheshire.core :as json]
            [clojure.set :refer [rename-keys]]))

(defn json->map
  [data]
    (json/parse-string data #(keyword %)))

(defn hifen->underscore!
  [string]
  (clojure.string/replace string #"-" "_"))

(defn underscore->hifen!
  [string]
  (clojure.string/replace string #"_" "-"))

(defn transform-column
  [v function]
  (rename-keys v (reduce #(assoc %1 (first %2) (keyword (function (name (first %2)))))
                          {} v)))

(defn not-nil? [clause]
  (not (nil? clause)))