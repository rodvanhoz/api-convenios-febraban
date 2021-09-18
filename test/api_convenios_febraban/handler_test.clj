(ns api-convenios-febraban.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [api-convenios-febraban.handler :refer :all]
            [api-convenios-febraban.mocks.database :as mocks.database]
            [cheshire.core :as json]))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))
          body (json/parse-string (:body response) #(keyword %))]
          (let [response (app (mock/request :get "/"))]
            (is (= (:status response) 200))
            (is (= (:status body) "ok")))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))

(deftest should-get-all-convenios
  (testing "should get all convenios"
    (let [result (mocks.database/mock-get true)]
      (is (= (count result) 4)))))