(ns clickbait-detector.guardian
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]))

(def url "https://api.nextgen.guardianapps.co.uk")

(defn url-for-path [path] (str url "/" path "/lite.json"))

(defn get-path! [path]
  (let [url (url-for-path path)]
    (json/read-str
     (:body (client/get url {:accept :json}))
     :key-fn keyword)))

(def keys-to-take [:headline :id])

(defn- get-all-content [response]
  (->
    response
    :collections
    ((partial map :content))
    flatten
    ((partial map #(select-keys %1 keys-to-take)))
    vec))

(defn get-items-for-path! [path]
  (->
     (get-path! path)
     (get-all-content)))
