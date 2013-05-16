(ns pro.juxt.website
  (:require
   [ring.util.response :refer (redirect)]
   [lamina.core :refer (enqueue)]
   [clojure.java.io :refer (resource)]
   [stencil.core :as stencil]
   [io.pedestal.service.interceptor :refer (defhandler)]
   [io.pedestal.service.impl.interceptor :refer (interceptor)]
   [endophile.core :refer (mp to-clj)]
   [clojure.xml :refer (emit-element)])
  (:import (up.start Plugin)))

(defn markdown [content]
  (->> content resource slurp mp to-clj (map emit-element) dorun with-out-str))

(defn index-handler [req]
  (let [pagename (get-in req [:params :pagename])]
    {:status 200
     :headers {"Content-Type" "text/html"}
     :body (stencil/render-file "page.html"
                                {:markdown markdown
                                 :content (fn [] 
                                            (stencil/render-file "home.html" {:markdown markdown}))})}))

(defn ->index [req] (redirect "/index.html"))

(defrecord WebApplication [pctx]
  Plugin
  (start [_]
    (let [{:keys [bus]} pctx]
      (enqueue bus {:up/topic :up.http/add-webapp
                    :routes [:juxtweb
                             ["/" {:get 'pro.juxt.website/->index}]
                             ["/index.html" {:get 'pro.juxt.website/index-handler}]]}))))
