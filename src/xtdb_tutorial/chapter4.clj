(ns xtdb-tutorial.chapter4
  (:require [xtdb.api :as xt]
            [xtdb-tutorial.core :as core]))

;; chapter 4 Bitemporality with XTDB

(defn jay-rose-insurance-history [node]
  (xt/submit-tx
   node
   [[::xt/put ;; (1)
     {:xt/id :consumer/RJ29sUU
      :consumer-id :RJ29sUU
      :first-name "Jay"
      :last-name "Rose"
      :cover? true
      :cover-type :Full}
     #inst "2113-12-03"                        ;; Valid time start
     #inst "2114-12-03"]                       ;; Valid time end

    [::xt/put ;; (2)
     {:xt/id :consumer/RJ29sUU
      :consumer-id :RJ29sUU
      :first-name "Jay"
      :last-name "Rose"
      :cover? true
      :cover-type :Full}
     #inst "2112-12-03"
     #inst "2113-12-03"]

    [::xt/put ;; (3)
     {:xt/id :consumer/RJ29sUU
      :consumer-id :RJ29sUU
      :first-name "Jay"
      :last-name "Rose"
      :cover? false}
     #inst "2112-06-03"
     #inst "2112-12-02"]

    [::xt/put ;; (4)
     {:xt/id :consumer/RJ29sUU
      :consumer-id :RJ29sUU
      :first-name "Jay"
      :last-name "Rose"
      :cover? true
      :cover-type :Promotional}
     #inst "2111-06-03"
     #inst "2112-06-03"]]))

(defn full-cover
  "Showing date when Jay had a full insurance cover"
  []
  (xt/q (xt/db core/node #inst "2114-01-01")
        '{:find [cover type]
          :where [[e :consumer-id :RJ29sUU]
                  [e :cover? cover]
                  [e :cover-type type]]}))

(defn different-type-of-cover
  "Showing date when Jay had a different type of cover"
  []
  (xt/q (xt/db core/node #inst "2111-07-03")
        '{:find [cover type]
          :where [[e :consumer-id :RJ29sUU]
                  [e :cover? cover]
                  [e :cover-type type]]}))

(defn no-cover
  "Showing date when Jay had no cover"
  []
  (xt/q (xt/db core/node #inst "2112-07-03")
        '{:find [cover type]
          :where [[e :consumer-id :RJ29sUU]
                  [e :cover? cover]
                  [e :cover-type type]]}))

