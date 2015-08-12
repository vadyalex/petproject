(defproject petproject "0.1.0-SNAPSHOT"

  :description "petproject"

  :min-lein-version "2.0.0"

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.3.1"]
                 [ring/ring-defaults "0.1.2"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/tools.logging "0.3.1"]]
;;                 [ch.qos.logback/logback-classic "1.0.1"]]

  :plugins [[lein-ring "0.8.13"]]

  :ring {:port 9080
         :init petproject.handler/myinit
         :destroy petproject.handler/mydestroy
         :handler petproject.handler/app}

  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
