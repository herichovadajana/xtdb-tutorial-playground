(ns xtdb-tutorial.chapter3
  (:require [xtdb.api :as xt]
            [xtdb-tutorial.core :as core]))

;; chapter 3 - Datalog

(def data
  [{:xt/id :commodity/Pu
    :common-name "Plutonium"
    :type :element/metal
    :density 19.816
    :radioactive true}

   {:xt/id :commodity/N
    :common-name "Nitrogen"
    :type :element/gas
    :density 1.2506
    :radioactive false}

   {:xt/id :commodity/CH4
    :common-name "Methane"
    :type :molecule/gas
    :density 0.717
    :radioactive false}

   {:xt/id :commodity/Au
    :common-name "Gold"
    :type :element/metal
    :density 19.300
    :radioactive false}

   {:xt/id :commodity/C
    :common-name "Carbon"
    :type :element/non-metal
    :density 2.267
    :radioactive false}

   {:xt/id :commodity/borax
    :common-name "Borax"
    :IUPAC-name "Sodium tetraborate decahydrate"
    :other-names ["Borax decahydrate" "sodium borate" "sodium tetraborate" "disodium tetraborate"]
    :type :mineral/solid
    :appearance "white solid"
    :density 1.73
    :radioactive false}])

;; to input data we will use easy-ingest function (easy-ingest node data)

(defn find-metal []
  (xt/q (xt/db core/node)
        '{:find [element]
          :where [[element :type :element/metal]]}))


(defn return-names-of-metal-elements []
  (xt/q (xt/db core/node)
        '{:find [name]
          :where [[e :type :element/metal]
                  [e :common-name name]]}))


(defn return-densities-and-names []
  (xt/q (xt/db core/node)
        '{:find [name rho]
          :where [[e :density rho]
                  [e :common-name name]]}))

(defn return-names-of-metal-elements-using-in []
  (xt/q (xt/db core/node)
        '{:find [name]
          :where [[e :type type]
                  [e :common-name name]]
          :in [type]}
        :element/metal))

(defn filter-type [type]
  (xt/q (xt/db core/node)
        '{:find [name]
          :where [[e :type type]
                  [e :common-name name]]
          :in [type]}
        type))

(defn filter-appearance [appearance]
  (xt/q (xt/db core/node)
        '{:find [name IUPAC]
          :where [[e :common-name name]
                  [e :appearance appearance]
                  [e :IUPAC-name IUPAC]]
          :in [appearance]}
        appearance))
