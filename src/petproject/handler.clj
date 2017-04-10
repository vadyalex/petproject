(ns petproject.handler

  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [clojure.data.json :as json]
            [clojure.tools.logging :as log]))


(defn myinit
  "Initializing.."
  []
  (log/info "Initializing.."))

(defn mydestroy
  "Clean up resources.."
  []
  (log/info "Clean up resources.."))

(defn myresource
  "Serve my main resource /myresource"
  []
  (do
    (log/info " -> serving /myresource")
    (json/write-str {:message "Hello Word"})))


(defroutes app-routes
  (GET "/myresource" [] (myresource))
  (context "/some/:where" [where]
           (GET "/" [] (do
                         (log/info " -> " where)
                         (str "THE ROOT /some/" where)))
           (GET "/here" [] (str "THE /some/" where "/here"))
           (GET "/there" [] (str "THE /some/" where "/there"))
           )

  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes api-defaults))
