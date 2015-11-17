(defproject clickbait-detector "0.0.1"
  :description "Guardian clickbait detector"
  :url "http://github.com/janua/clickbait-detector"
  :license {:name "MIT License"
            :url "http://choosealicense.com/licenses/mit/"}
  :min-lein-version "2.5.3"

  :dependencies [[org.clojure/clojure "1.7.0-beta3"]
                 [org.clojure/clojurescript "1.7.170"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [org.clojure/tools.logging "0.3.1"]
                 [http-kit "2.1.19"]
                 [compojure "1.4.0"]
                 [ring/ring-core "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
                 [ring/ring-devel "1.4.0"]
                 ;[ring.middleware.reload :as reload] ;Used for live reloading at main
                 [cljs-http "0.1.37"]
                 [hiccup "1.0.5"]
                 [clj-http "2.0.0"]
                 [org.clojure/data.json "0.2.6"]]

  :plugins [[lein-cljsbuild "1.1.1"]
            [lein-figwheel "0.5.0-1"]]

  :main clickbait-detector.handler

  :source-paths ["src"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :cljsbuild {
    :builds [{:id "dev"
              :source-paths ["src"]

              :figwheel { :on-jsload "clickbait-detector.core/on-js-reload" }

              :compiler {:main clickbait-detector.core
                         :asset-path "/js/compiled/out"
                         :output-to "resources/public/js/compiled/clickbait-detector.js"
                         :output-dir "resources/public/js/compiled/out"
                         :source-map-timestamp true }}
             {:id "min"
              :source-paths ["src"]
              :compiler {:output-to "resources/public/js/compiled/clickbait-detector.js"
                         :main clickbait-detector.core
                         :optimizations :advanced
                         :pretty-print false}}]}

  :figwheel {
             ;; :http-server-root "public" ;; default and assumes "resources"
             ;; :server-port 3449 ;; default
             ;; :server-ip "127.0.0.1"

             :css-dirs ["resources/public/css"] ;; watch and update CSS
             ;:http-server-root clickbait-detector.handler/handler ;"public"
             :server-port 8000
             ;; Start an nREPL server into the running figwheel process
             ;; :nrepl-port 7888

             ;; Server Ring Handler (optional)
             ;; if you want to embed a ring handler into the figwheel http-kit
             ;; server, this is for simple ring servers, if this
             ;; doesn't work for you just run your own server :)
             :ring-handler clickbait-detector.handler/handler

             ;; To be able to open files in your editor from the heads up display
             ;; you will need to put a script on your path.
             ;; that script will have to take a file path and a line number
             ;; ie. in  ~/bin/myfile-opener
             ;; #! /bin/sh
             ;; emacsclient -n +$2 $1
             ;;
             ;; :open-file-command "myfile-opener"

             ;; if you want to disable the REPL
             ;; :repl false

             ;; to configure a different figwheel logfile path
             ;; :server-logfile "tmp/logs/figwheel-logfile.log"
             })
