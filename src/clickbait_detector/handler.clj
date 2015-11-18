(ns clickbait-detector.handler
  (:use org.httpkit.server)
  (:require [ring.middleware.reload :as reload]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [compojure.route :as route]
            [compojure.core :refer [GET POST defroutes]]
            [compojure.handler :refer [site]]
            [clojure.tools.logging :refer [info]]
            [clickbait-detector.templates :as templates]
            [clickbait-detector.guardian :as guardian]))

(defn maybe-add-clickbait [item]
  (if (.contains (:headline item) "as")
    (assoc item :clickbait true)
    item))

(defn check-clickbait [items]
  (map maybe-add-clickbait items))

(defroutes app-routes
  (GET "/" [] "Welcome!")
  (GET "/static/*" [& glob]
       (let [path (:* glob)]
         (templates/main-html (check-clickbait (guardian/get-items-for-path! path)))))
  (GET "/get/*" [& glob]
       (let [path (:* glob)]
         (templates/main-html-with-reagent (check-clickbait (guardian/get-items-for-path! path)))))
  (route/files "" {:root "resources"})
  (route/files "" {:root "resources"})
  (route/resources "/")
  (route/not-found "Page not found!"))


(def app
  (-> app-routes
      (wrap-file-info)))

(def in-dev? true)

(def handler (if in-dev?
               (reload/wrap-reload (site #'app-routes)) ;; only reload when dev
               (site app-routes)))

(defn -main [& args]
  (run-server handler {:port 8080})
  (info "Server started. http://127.0.0.1:8080"))
