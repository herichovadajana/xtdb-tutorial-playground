(ns xtdb-tutorial.chapter5
  (:require [xtdb.api :as xt]
            [xtdb-tutorial.core :as core]))

;; Chapter 5 - Match with XTDB


(def companies-data
  [{:xt/id :gold-harmony
    :company-name "Gold Harmony"
    :seller? true
    :buyer? false
    :units/Au 10211
    :credits 51}

   {:xt/id :tombaugh-resources
    :company-name "Tombaugh Resources Ltd."
    :seller? true
    :buyer? false
    :units/Pu 50
    :units/N 3
    :units/CH4 92
    :credits 51}

   {:xt/id :encompass-trade
    :company-name "Encompass Trade"
    :seller? true
    :buyer? true
    :units/Au 10
    :units/Pu 5
    :units/CH4 211
    :credits 1002}

   {:xt/id :blue-energy
    :seller? false
    :buyer? true
    :company-name "Blue Energy"
    :credits 1000}])

(defn match-operation-for-a-valid-transaction
  [node]
  (xt/submit-tx
   node
   [[::xt/match
     :blue-energy
     {:xt/id :blue-energy
      :seller? false
      :buyer? true
      :company-name "Blue Energy"
      :credits 1000}]
    [::xt/put
     {:xt/id :blue-energy
      :seller? false
      :buyer? true
      :company-name "Blue Energy"
      :credits 900
      :units/CH4 10}]

    [::xt/match
     :tombaugh-resources
     {:xt/id :tombaugh-resources
      :company-name "Tombaugh Resources Ltd."
      :seller? true
      :buyer? false
      :units/Pu 50
      :units/N 3
      :units/CH4 92
      :credits 51}]
    [::xt/put
     {:xt/id :tombaugh-resources
      :company-name "Tombaugh Resources Ltd."
      :seller? true
      :buyer? false
      :units/Pu 50
      :units/N 3
      :units/CH4 82
      :credits 151}]]))


(defn match-operation-for-a-non-valid-transaction
  [node]
  (xt/submit-tx
   node
   [[::xt/match
     :gold-harmony
     {:xt/id :gold-harmony
      :company-name "Gold Harmony"
      :seller? true
      :buyer? false
      :units/Au 10211
      :credits 51}]
    [::xt/put
     {:xt/id :gold-harmony
      :company-name "Gold Harmony"
      :seller? true
      :buyer? false
      :units/Au 211
      :credits 51}]

    [::xt/match
     :encompass-trade
     {:xt/id :encompass-trade
      :company-name "Encompass Trade"
      :seller? true
      :buyer? true
      :units/Au 10
      :units/Pu 5
      :units/CH4 211
      :credits 100002}]
    [::xt/put
     {:xt/id :encompass-trade
      :company-name "Encompass Trade"
      :seller? true
      :buyer? true
      :units/Au 10010
      :units/Pu 5
      :units/CH4 211
      :credits 1002}]]))


(defn stock-check
  [company-id item]
  {:result (xt/q (xt/db core/node)
                 {:find '[name funds stock]
                  :where ['[e :company-name  name]
                          '[e :credits funds]
                          ['e item 'stock]]
                  :in '[e]}
                 company-id)
   :item item})

(defn format-stock-check
  [{:keys [result item] :as stock-check}]
  (for [[name funds commod] result]
    (str "Name: " name ", Funds: " funds ", " item ", " commod)))


(defn check-secret-note
  "Check if there is a secret note in cargo in manifest"
  []
  (xt/q (xt/db core/node)
        '{:find [belongings]
          :where [[e :cargo belongings]]
          :in [belongings]}
        "secret note"))

