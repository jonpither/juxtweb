(ns pro.juxt.website
  (:require 
   [io.pedestal.service.interceptor :refer (defhandler)]))

(defn index [req]
  {:status 200 :headers {"Content-Type" "text/html"} :body "<h1>JUXT</h1>"})
