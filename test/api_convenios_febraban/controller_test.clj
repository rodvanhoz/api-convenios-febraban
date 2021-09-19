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
        (is (= (count result) 5))))))

(deftest should-get-by-uuid
  (testing "should get by uuid"
    (with-redefs [db.febraban/get (fn [clauses] (mocks.database/mock-get clauses))]
      (let [result (get-by-uuid "50888255-1906-433e-b478-f9c48da8b9b7")]
        (is (= (count result) 1))
        (is (= (-> result (first) (:uuid result)) "50888255-1906-433e-b478-f9c48da8b9b7"))
        (is (= (-> result (first) (:segmento result)) "1"))
        (is (= (-> result (first) (:cod-convenio result)) "0000"))
        (is (= (-> result (first) (:nome result)) "Prefeitura Municipal de São Paulo"))
        (is (= (-> result (first) (:uf result)) "SP"))))))

(deftest should-get-by-cod-convenio
  (testing "should get by cod-convenio"
    (with-redefs [db.febraban/get (fn [clauses] (mocks.database/mock-get clauses))]
      (let [result (get-by-cod-convenio "0040")]
        (is (= (count result) 1))
        (is (= (-> result (first) (:uuid result)) "782ba534-43dd-4995-a3f2-7879a1d4c551"))
        (is (= (-> result (first) (:segmento result)) "2"))
        (is (= (-> result (first) (:cod-convenio result)) "0040"))
        (is (= (-> result (first) (:nome result)) "DAERP - RIBEIRÃO PRETO"))
        (is (= (-> result (first) (:uf result)) "SP")))))
        
  (testing "testing get by cod-convenio and segmento"
    (with-redefs [db.febraban/get (fn [clauses] (mocks.database/mock-get clauses))]
      (let [result (get-by-cod-convenio "0262" "5")]
        (is (= (count result) 1))
        (is (= (-> result (first) (:uuid result)) "eeec1d56-dbe6-4e64-91a4-5be9b8d3e2ed"))
        (is (= (-> result (first) (:segmento result)) "5"))
        (is (= (-> result (first) (:cod-convenio result)) "0262"))
        (is (= (-> result (first) (:nome result)) "TESTE FEBRABAN SEGMENTO 5"))
        (is (= (-> result (first) (:uf result)) "SP"))))))