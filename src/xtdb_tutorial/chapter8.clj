(ns xtdb-tutorial.chapter6
  (:require [xtdb.api :as xt]
            [xtdb-tutorial.core :as core]))

;; Chapter 8 - Await with XTDB

(def stats
  [{:body "Sun"
    :type "Star"
    :units {:radius "Earth Radius"
            :volume "Earth Volume"
            :mass "Earth Mass"
            :gravity "Standard gravity (g)"}
    :radius 109.3
    :volume 1305700
    :mass 33000
    :gravity 27.9
    :xt/id :Sun}
   {:body "Jupiter"
    :type "Gas Giant"
    :units {:radius "Earth Radius"
            :volume "Earth Volume"
            :mass "Earth Mass"
            :gravity "Standard gravity (g)"}
    :radius 10.97
    :volume 1321
    :mass 317.83
    :gravity 2.52
    :xt/id :Jupiter}
   {:body "Saturn"
    :type "Gas Giant"
    :units {:radius "Earth Radius"
            :volume "Earth Volume"
            :mass "Earth Mass"
            :gravity "Standard gravity (g)"}
    :radius :volume
    :mass :gravity
    :xt/id :Saturn}
   {:body "Saturn"
    :units {:radius "Earth Radius"
            :volume "Earth Volume"
            :mass "Earth Mass"
            :gravity "Standard gravity (g)"}
    :radius 9.14
    :volume 764
    :mass 95.162
    :gravity 1.065
    :type "planet"
    :xt/id :Saturn}
   {:body "Uranus"
    :units {:radius "Earth Radius"
            :volume "Earth Volume"
            :mass "Earth Mass"
            :gravity "Standard gravity (g)"}
    :radius 3.981
    :volume 63.1
    :mass 14.536
    :gravity 0.886
    :type "planet"
    :xt/id :Uranus}
   {:body "Neptune"
    :units {:radius "Earth Radius"
            :volume "Earth Volume"
            :mass "Earth Mass"
            :gravity "Standard gravity (g)"}
    :radius 3.865
    :volume 57.7
    :mass 17.147
    :gravity 1.137
    :type "planet"
    :xt/id :Neptune}
   {:body "Earth"
    :units {:radius "Earth Radius"
            :volume "Earth Volume"
            :mass "Earth Mass"
            :gravity "Standard gravity (g)"}
    :radius 1
    :volume 1
    :mass 1
    :gravity 1
    :type "planet"
    :xt/id :Earth}
   {:body "Venus"
    :units {:radius "Earth Radius"
            :volume "Earth Volume"
            :mass "Earth Mass"
            :gravity "Standard gravity (g)"}
    :radius 0.9499
    :volume 0.857
    :mass 0.815
    :gravity 0.905
    :type "planet"
    :xt/id :Venus}
   {:body "Mars"
    :units {:radius "Earth Radius"
            :volume "Earth Volume"
            :mass "Earth Mass"
            :gravity "Standard gravity (g)"}
    :radius 0.532
    :volume 0.151
    :mass 0.107
    :gravity 0.379
    :type "planet"
    :xt/id :Mars}
   {:body "Ganymede"
    :units {:radius "Earth Radius"
            :volume "Earth Volume"
            :mass "Earth Mass"
            :gravity "Standard gravity (g)"}
    :radius 0.4135
    :volume 0.0704
    :mass 0.0248
    :gravity 0.146
    :type "moon"
    :xt/id :Ganymede}
   {:body "Titan"
    :units {:radius "Earth Radius"
            :volume "Earth Volume"
            :mass "Earth Mass"
            :gravity "Standard gravity (g)"}
    :radius 0.4037
    :volume 0.0658
    :mass 0.0225
    :gravity 0.138
    :type "moon"
    :xt/id :Titan}
   {:body "Mercury"
    :units {:radius "Earth Radius"
            :volume "Earth Volume"
            :mass "Earth Mass"
            :gravity "Standard gravity (g)"}
    :radius 0.3829
    :volume 0.0562
    :mass 0.0553
    :gravity 0.377
    :type "planet"
    :xt/id :Mercury}])

(defn put-stats-into-db []
  (xt/submit-tx core/node (mapv (fn [stat] [::xt/put stat]) stats)))

(defn add-kepra-into-db []
  (xt/submit-tx
   core/node
   [[::xt/put
     {:body "Kepra-5"
      :units {:radius "Earth Radius"
              :volume "Earth Volume"
              :mass "Earth Mass"
              :gravity "Standard gravity (g)"}
      :radius 0.6729
      :volume 0.4562
      :mass 0.5653
      :gravity 1.4
      :type "planet"
      :xt/id :Kepra-5}]]))

(defn sort-planets-by-gravity []
  (sort (xt/q (xt/db core/node)
              '{:find [gravity planet]
                :where [[planet :gravity gravity]]})))

(def test-traveller
  {:xt/id :origin-planet/test-traveller
   :chosen-name "Test"
   :given-name "Test Traveller"
   :passport-number (java.util.UUID/randomUUID)
   :stamps []
   :penalties []})

(defn ingest-and-query-traveller-doc
  [traveller-doc]
  (xt/await-tx core/node (xt/submit-tx core/node [[::xt/put traveller-doc]]))
  (xt/q
   (xt/db core/node)
   '{:find [n]
     :where [[id :passport-number n]]
     :in [id]}
   (:xt/id traveller-doc)))

