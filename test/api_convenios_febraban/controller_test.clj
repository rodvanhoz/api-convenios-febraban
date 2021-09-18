(ns api-convenios-febraban.controller-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [api-convenios-febraban.controller.febraban :refer :all]
            [api-convenios-febraban.db.febraban :as db.febraban]
            [api-convenios-febraban.mocks.database :as mocks.database]))

(deftest should-get-all-convenios
  (testing "should get all convenios"
    (with-redefs [db.febraban/get (fn [clauses] (mocks.database/mock-get clauses))]
      (let [result (get-all)]
        (is (= (count result) 4))))))

(deftest should-get-by-uuid
  (testing "should get by uuid"
    (with-redefs [db.febraban/get (fn [clauses] (mocks.database/mock-get clauses))]
      (let [result (get-by-uuid "50888255-1906-433e-b478-f9c48da8b9b7")]
        (is (= (count result) 1))
        (is (= (-> result (first) (:uuid result)) "50888255-1906-433e-b478-f9c48da8b9b7"))
        (is (= (-> result (first) (:segmento result)) "1"))
        (is (= (-> result (first) (:cod-convenio result)) "0000"))
        (is (= (-> result (first) (:nome result)) "Prefeitura Municipal de São Paulo"))))))