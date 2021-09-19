(ns api-convenios-febraban.mocks.database
  (:require [api-convenios-febraban.utils.uuids :refer [uuid-from-string]]
            [api-convenios-febraban.utils.adapters :refer [not-nil?]]))

(def data (list {:uuid "50888255-1906-433e-b478-f9c48da8b9b7" :segmento "1" :cod_convenio "0000" :nome "Prefeitura Municipal de São Paulo" :uf "SP"}
                {:uuid "782ba534-43dd-4995-a3f2-7879a1d4c551" :segmento "2" :cod_convenio "0040" :nome "DAERP - RIBEIRÃO PRETO" :uf "SP"}
                {:uuid "3c2fd625-2540-46cf-909e-027b4c61803a" :segmento "4" :cod_convenio "0001" :nome "CETERP - CENTRAL TELEFÔNICA DE RIBEIRÃO PRETO" :uf "SP"}
                {:uuid "f0b6fa79-e1da-4fd3-8676-d04f7164da35" :segmento "4" :cod_convenio "0262" :nome "NET RIBEIRÃO PRETO S.A" :uf "SP"}
                {:uuid "eeec1d56-dbe6-4e64-91a4-5be9b8d3e2ed" :segmento "5" :cod_convenio "0262" :nome "TESTE FEBRABAN SEGMENTO 5" :uf "SP"}))

(defn- select-get [data]
  (doall (map (fn [v]
                (-> {}
                    (assoc :uuid (:uuid v))
                    (assoc :segmento (:segmento v))
                    (assoc :cod-convenio (:cod_convenio v))
                    (assoc :nome (:nome v))
                    (assoc :uf (:uf v))))
            data)))


(defn- mock-get-by-uuid [uuid]
  (select-get (filter #(= (str uuid) (:uuid %)) data)))

(defn- mock-get-by-cod-convenio [cod-convenio]
  (select-get (filter #(= cod-convenio (:cod_convenio %)) data)))

(defn- mock-get-by-cod-convenio-and-segmento [{:keys [cod_convenio segmento]}]
  (let [result (filter #(= cod_convenio (:cod_convenio %)) data)]
    (select-get (filter #(= segmento (:segmento %)) result))))

(defn mock-get [clauses]
  (if (not-nil? (:uuid clauses))
    (mock-get-by-uuid (:uuid clauses))
    (if (not-nil? (:cod_convenio clauses))
      (if (not-nil? (:segmento clauses))
        (mock-get-by-cod-convenio-and-segmento clauses)
        (mock-get-by-cod-convenio (:cod_convenio clauses)))
      (select-get data))))
  
