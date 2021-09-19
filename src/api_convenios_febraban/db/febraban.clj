(ns api-convenios-febraban.db.febraban
  (:require [korma.db :refer :all]
    [korma.core :refer :all]
    [clojure.java.jdbc :as sql]
    [clojure.tools.logging :as log]
    [api-convenios-febraban.db :as db]
    [api-convenios-febraban.db.entities :as e]
    [api-convenios-febraban.utils.uuids :as utils.uuids]
    [api-convenios-febraban.utils.adapters :refer [not-nil?]]))

(defn get
  [clauses]
  (select e/febraban
    (fields :uuid :segmento [:cod_convenio :cod-convenio] :nome :uf)
    (where clauses)))

(defn by-uuid [uuid]
  (get {:uuid (utils.uuids/uuid-from-string uuid)}))

(defn by-cod-convenio 
  [cod-convenio segmento]
  (get (-> {}
           (cond-> (not-nil? cod-convenio) (assoc :cod_convenio cod-convenio))
           (cond-> (not-nil? segmento) (assoc :segmento segmento)))))

(defn all []
  (get true))