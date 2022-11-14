(ns xtdb-tutorial.core
  (:require [xtdb.api :as xt]))

;; Chapter 1

(def node (xt/start-node {}))

(def manifest
  (xt/submit-tx
   node
   [[::xt/put
     {:xt/id :manifest
      :pilot-name "Johanna"
      :id/rocket "SB002-sol"
      :id/employee "22910x2"
      :badges ["SETUP" "PUT" "DATALOG-QUERIES" "BITEMP" "MATCH"]
      :cargo ["stereo" "gold fish" "slippers" "secret note"]}]]))

;; to submit manifest to db: (xt/submit-tx node [[::xt/put manifest-dajana]])
;; to get info about manifest from db: (xt/entity (xt/db node) :manifest)
;; make sure that node indexes are caught up with the latest transaction: (xt/sync node)






