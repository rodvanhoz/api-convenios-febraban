(ns api-convenios-febraban.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [clojure.tools.logging :as log]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.util.http-response :refer [ok bad-request unauthorized not-found created no-content conflict]]
            [api-convenios-febraban.controller.febraban :as controller.febraban]))

(defn home
  [_]
  {:status 200
    :body {:status "ok"}})

(defn- convenios-get-all []
  (controller.febraban/get-all))

(defn- convenios-get-by-uuid [uuid]
  (controller.febraban/get-by-uuid uuid))

(defn- convenios-get-by-cod-convenio [cod-convenio]
  (controller.febraban/get-by-cod-convenio cod-convenio))

(defn- convenios-get-by-segmento-and-cod-convenio [segmento cod-convenio]
  (controller.febraban/get-by-cod-convenio cod-convenio segmento))

(defroutes app-routes
  (GET "/" [] home)
  (context "/api" []
    (context "/febraban" []
      (GET "/" [] (convenios-get-all))
      (GET "/:uuid" [uuid] (convenios-get-by-uuid uuid))
      (GET "/cod-convenio/:cod-convenio" [cod-convenio] (convenios-get-by-cod-convenio cod-convenio))
      
      (context "/segmento" []
        (GET "/:segmento/cod-convenio/:cod-convenio" [segmento cod-convenio] (convenios-get-by-segmento-and-cod-convenio segmento cod-convenio))))))

(def app
 (-> (wrap-defaults app-routes site-defaults)
     (wrap-cors :access-control-allow-origin #"http://localhost:4200" :access-control-allow-methods [:get :put :post :delete])
     (wrap-json-response)
     (wrap-json-body {:keywords? true})))
