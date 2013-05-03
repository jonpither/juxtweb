(require '(clojure [string :refer (join)]
                   [edn :as edn])
         '(clojure.java [shell :refer (sh)]))

(def version
  (let [{:keys [exit out err]} (sh "git" "describe" "--tags" "--long")] 
    (if (= 128 exit) "0.0.1"
        (let [[[_ tag commits hash]] (re-seq #"(.*)-(.*)-(.*)" out)]
          (if (zero? (edn/read-string commits)) 
            tag 
            (let [[[_ stem lst]] (re-seq #"(.*\.)(.*)" tag)]
              (join [stem (inc (read-string lst)) "-" "SNAPSHOT"])))))))

(def versions {:up "0.0.1"})

(defproject pro.juxt/juxtweb version
;;  :eval-in :classloader
  :plugins [[lein-up ~(versions :up)]]
  :dependencies [[org.clojure/clojure "1.5.1"]]

  :up {:plugins {[up/up-logging ~(versions :up)] nil
                 [up/up-http ~(versions :up)] {:port 8787}
                 [up/up-nrepl ~(versions :up)] {:port 6011}
                 [up/up-pedestal-webapp ~(versions :up)] {:handler pro.juxt.website/index}
                 }})
