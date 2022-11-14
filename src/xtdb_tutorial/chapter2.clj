(ns xtdb-tutorial.chapter2
  (:require [xtdb.api :as xt]
            [xtdb-tutorial.core :as core]))

;; Chapter 2 - Put

(def comodities-stocks
  "Submit comodities into db"
  (xt/submit-tx
   core/node
   [[::xt/put
     {:xt/id :commodity/Pu
      :common-name "Plutonium"
      :type :element/metal
      :density 19.816
      :radioactive true}]

    [::xt/put
     {:xt/id :commodity/N
      :common-name "Nitrogen"
      :type :element/gas
      :density 1.2506
      :radioactive false}]

    [::xt/put
     {:xt/id :commodity/CH4
      :common-name "Methane"
      :type :molecule/gas
      :density 0.717
      :radioactive false}]]))


(def plutonium-stocks
  (xt/submit-tx
   core/node
   [[::xt/put
     {:xt/id :stock/Pu
      :commod :commodity/Pu
      :weight-ton 21 }
     #inst "2115-02-13T18"]

    [::xt/put
     {:xt/id :stock/Pu
      :commod :commodity/Pu
      :weight-ton 23 }
     #inst "2115-02-14T18"]

    [::xt/put
     {:xt/id :stock/Pu
      :commod :commodity/Pu
      :weight-ton 22.2 }
     #inst "2115-02-15T18"]

    [::xt/put
     {:xt/id :stock/Pu
      :commod :commodity/Pu
      :weight-ton 24 }
     #inst "2115-02-18T18"]

    [::xt/put
     {:xt/id :stock/Pu
      :commod :commodity/Pu
      :weight-ton 24.9 }
     #inst "2115-02-19T18"]]))

(def nitrogen-methane-stock
  (xt/submit-tx
   core/node
   [[::xt/put
     {:xt/id :stock/N
      :commod :commodity/N
      :weight-ton 3 }
     #inst "2115-02-13T18"
     #inst "2115-02-19T18"]

    [::xt/put
     {:xt/id :stock/CH4
      :commod :commodity/CH4
      :weight-ton 92 }
     #inst "2115-02-15T18"
     #inst "2115-02-19T18"]]))

(defn easy-ingest
  "Uses XTDB put transaction to add a vector of documents to a specified
  node"
  [node docs]
  (xt/submit-tx node
                (vec (for [doc docs]
                       [::xt/put doc])))
  (xt/sync node))

