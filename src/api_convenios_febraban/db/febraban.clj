(ns api-convenios-febraban.db.febraban
  (:require [korma.db :refer :all]
    [korma.core :refer :all]
    [clojure.java.jdbc :as sql]
    [clojure.tools.logging :as log]
    [api-convenios-febraban.db :as db]
    [api-convenios-febraban.db.entities :as e]
    [api-convenios-febraban.utils.uuids :as utils.uuids]))

(defn get
  [clauses]
  (select e/febraban
    (fields :uuid :segmento :cod-convenio :nome)
    (where clauses)))

(defn by-uuid [uuid]
  (get {:uuid (utils.uuids/uuid-from-string uuid)}))

(defn by-cod-convenio [cod-convenio]
  (get {:cod-convenio cod-convenio}))

(defn all []
  (get true))