(ns api-convenios-febraban.db
  (:require [korma.db :as korma :refer [defdb]]))

  ;; you can also specify a full connection string if you'd prefer:
  (def pg-uri
    {:connection-uri (str "postgresql://ieyunibbownizx:b7451ea78b286715bb4e0218544118d8be0c1d6118106a90c8713425c8514650@ec2-23-21-229-200.compute-1.amazonaws.com:5432/df7qbjv2f489d3"
                          "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory")})
  
(defn- db-conn []
  (korma/postgres
      {:db "df7qbjv2f489d3" 
       :user "ieyunibbownizx"
       :password "b7451ea78b286715bb4e0218544118d8be0c1d6118106a90c8713425c8514650" 
       :host "ec2-23-21-229-200.compute-1.amazonaws.com" 
       :port "5432"
       :ssl true
       :sslfactory "org.postgresql.ssl.NonValidatingFactory"}))

(defdb db (db-conn))
