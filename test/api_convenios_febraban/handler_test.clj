(ns api-convenios-febraban.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [api-convenios-febraban.handler :refer :all]
            [api-convenios-febraban.mocks.database :as mocks.database]
            [api-convenios-febraban.db.febraban :as db.febraban]
            [cheshire.core :as json]))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))
          body (json/parse-string (:body response) #(keyword %))]
          (let [response (app (mock/request :get "/"))]
            (is (= (:status response) 200))
            (is (= (:status body) "ok"))))))

(deftest should-get-all-convenios
  (testing "should get all convenios"
    (with-redefs [db.febraban/get (fn [clauses] (mocks.database/mock-get clauses))]
      (let [response (app (mock/request :get "/api/febraban"))
              body (json/parse-string (:body response) #(keyword %))]
        (is (= (:status response) 200))
        (is (= (count body) 4))))))

(deftest should-get-by-uuid
  (testing "should get by uuid"
    (with-redefs [db.febraban/get (fn [clauses] (mocks.database/mock-get clauses))]
      (let [response (app (mock/request :get "/api/febraban/3c2fd625-2540-46cf-909e-027b4c61803a"))
              body (json/parse-string (:body response) #(keyword %))]
        (is (= (:status response) 200))
        (is (= (count body) 1))
        (is (= (-> body (first) (:uuid body)) "3c2fd625-2540-46cf-909e-027b4c61803a"))
        (is (= (-> body (first) (:segmento body)) "4"))
        (is (= (-> body (first) (:cod-convenio body)) "0001"))
        (is (= (-> body (first) (:nome body)) "CETERP - CENTRAL TELEFÔNICA DE RIBEIRÃO PRETO"))
        (is (= (-> body (first) (:uf body)) "SP"))))))

(deftest should-get-by-cod-convenio
  (testing "should get by cod-covenio"
    (with-redefs [db.febraban/get (fn [clauses] (mocks.database/mock-get clauses))]
      (let [response (app (mock/request :get "/api/febraban/cod-convenio/0262"))
              body (json/parse-string (:body response) #(keyword %))]
        (is (= (:status response) 200))
        (is (= (count body) 1))
        (is (= (-> body (first) (:uuid body)) "f0b6fa79-e1da-4fd3-8676-d04f7164da35"))
        (is (= (-> body (first) (:segmento body)) "4"))
        (is (= (-> body (first) (:cod-convenio body)) "0262"))
        (is (= (-> body (first) (:nome body)) "NET RIBEIRÃO PRETO S.A"))
        (is (= (-> body (first) (:uf body)) "SP"))))))