(ns api-convenios-febraban.controller.febraban
  (:require [api-convenios-febraban.db.febraban :as db.febraban]))

(defn get-all []
  (db.febraban/all))

(defn get-by-uuid [uuid]
  (db.febraban/by-uuid uuid))