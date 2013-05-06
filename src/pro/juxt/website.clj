(ns pro.juxt.website
  (:require 
   [stencil.core :as stencil]
   [io.pedestal.service.interceptor :refer (defhandler)]))

(defn index [req]
  {:status 200 
   :headers {"Content-Type" "text/html"} 
   :body (stencil/render-file "page.html" {:title "test title"})})
