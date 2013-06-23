(ns pro.juxt.website.events
  (:require
   [clj-time.format :as tf]
   [clj-time.core :as time]
   [clojure.java.io :as io :refer (resource)]
   [clojure.edn :as edn]
   [endophile.core :refer (mp to-clj)]
   [pro.juxt.website.util :refer (emit-element)]
   )
  (:import (org.joda.time DateTime LocalDateTime))
  )

(def date-formatter (tf/formatter "EEEE, d MMMM. HH:mm"))

(defn get-time [d]
  (.getTime d))

(defn render [ev]
  (str
   "<em>"
   (as-> ev %
         (:date %)
         (DateTime. %)
         (LocalDateTime. % (time/time-zone-for-id "Europe/London"))
         (.print date-formatter %)
         )
   "</em>"
   (->> ev :description mp to-clj (map emit-element) dorun with-out-str)))

(defn get-events [content]
  (let [now (.getTime (java.util.Date.))
        [prev-events upcoming-events] (split-with #(< ((comp get-time :date) %) now) (sort-by (comp get-time :date) (comparator <) (edn/read-string (slurp (resource "events.edn")))))]
    (str "<h4>Upcoming events</h4>"
         (apply str (map render upcoming-events))
         "<h4>Past events</h4>"
         (apply str (map render prev-events)))
    )
  )
