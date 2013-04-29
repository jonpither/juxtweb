(def version
  (let [{:keys [exit out err]} (sh "git" "describe" "--tags" "--long")] 
    (if (= 128 exit) "0.0.1"
        (let [[[_ tag commits hash]] (re-seq #"(.*)-(.*)-(.*)" out)]
          (if (zero? (edn/read-string commits)) 
            tag 
            (let [[[_ stem lst]] (re-seq #"(.*\.)(.*)" tag)]
              (join [stem (inc (read-string lst)) "-" "SNAPSHOT"])))))))

(defproject pro.juxt/juxtweb version
  :plugins [[lein-up "0.0.1"]]

)

