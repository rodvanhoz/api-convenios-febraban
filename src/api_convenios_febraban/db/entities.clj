(ns api-convenios-febraban.db.entities
  (:use korma.core api-convenios-febraban.db)
  (:require [korma.core :refer [defentity pk]]
            [api-convenios-febraban.utils.adapters :as utils.adapters]))

(declare febraban)

(defentity febraban
  (pk :uuid)
  (table :febraban)
  (transform (fn [v] (utils.adapters/transform-column v utils.adapters/underscore->hifen!))))