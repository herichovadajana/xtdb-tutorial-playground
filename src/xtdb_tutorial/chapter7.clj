(ns xtdb-tutorial.chapter7
  (:require [xtdb.api :as xt]
            [xtdb-tutorial.core :as core]))

;; Chapter 7 - Evict wth XTDB

(defn people-on-the-ship
  []
  (xt/submit-tx
   core/node
   [[::xt/put
     {:xt/id :person/kaarlang
      :full-name "Kaarlang"
      :origin-planet "Mars"
      :identity-tag :KA01299242093
      :DOB #inst "2040-11-23"}]

    [::xt/put
     {:xt/id :person/ilex
      :full-name "Ilex Jefferson"
      :origin-planet "Venus"
      :identity-tag :IJ01222212454
      :DOB #inst "2061-02-17"}]

    [::xt/put
     {:xt/id :person/thadd
      :full-name "Thad Christover"
      :origin-moon "Titan"
      :identity-tag :IJ01222212454
      :DOB #inst "2101-01-01"}]

    [::xt/put
     {:xt/id :person/johanna
      :full-name "Johanna"
      :origin-planet "Earth"
      :identity-tag :JA012992129120
      :DOB #inst "2090-12-07"}]]))

(defn full-query
  [node]
  (xt/q
   (xt/db node)
   '{:find [(pull e [*])]
     :where [[e :xt/id id]
             [e :full-name name]]}))


(defn evict-kaarlang
  []
  (xt/submit-tx core/node [[::xt/evict :person/kaarlang]]))

(defn kaarlang-history
  []
  (xt/entity-history (xt/db core/node)
                     :person/kaarlang
                     :desc
                     {:with-docs? true}))
