(ns xtdb-tutorial.chapter6
  (:require [xtdb.api :as xt]
            [xtdb-tutorial.core :as core]))

;; Chapter 6 - Delete with XTDB 

(defn kaarlang-client-history []
  (xt/submit-tx
   core/node
   [[::xt/put {:xt/id :kaarlang/clients
               :clients [:encompass-trade]}
     #inst "2110-01-01T09"
     #inst "2111-01-01T09"]

    [::xt/put {:xt/id :kaarlang/clients
               :clients [:encompass-trade :blue-energy]}
     #inst "2111-01-01T09"
     #inst "2113-01-01T09"]

    [::xt/put {:xt/id :kaarlang/clients
               :clients [:blue-energy]}
     #inst "2113-01-01T09"
     #inst "2114-01-01T09"]

    [::xt/put {:xt/id :kaarlang/clients
               :clients [:blue-energy :gold-harmony :tombaugh-resources]}
     #inst "2114-01-01T09"
     #inst "2115-01-01T09"]])
  )

(defn show-history []
  (xt/entity-history
   (xt/db core/node #inst "2116-01-01T09")
   :kaarlang/clients
   :desc
   {:with-docs? true}))

(defn delete-the-whole-client-history []
  (xt/submit-tx
   core/node
   [[::xt/delete :kaarlang/clients #inst "2110-01-01" #inst "2116-01-01"]]))
