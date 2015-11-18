(ns ^:figwheel-always clickbait-detector.core
  (:require [reagent.core :as r]
            [cljs.reader :refer [read-string]]
            [goog.dom :as dom]))

(enable-console-print!)

(defonce data (read-string (.-innerHTML (dom/getElement "data"))))

(defonce show (r/atom true))

(defn item [item]
  (let [{:keys [headline id]} item
        clickbait (if (:clickbait item)
                  "clickbait"
                  (if (true? @show)
                    "noclickbait"
                    "noclickbait noshow"))
        item-classes (str "item " clickbait)]
  [:div {:key id :class item-classes}
   [:p {:class "headline"} headline]
   [:aside {:class "section"} id]]))

(defn main-application [data]
  [:div
    [:div {:class "buttons"}
     [:h2 "Clickbait"]
     [:button {:on-click #(reset! show true)} "Show"]
     [:button {:on-click #(reset! show false)} "Hide"]]
    [:div
     (doall (map item data))]])

(r/render [main-application data]
  (js/document.getElementById "application"))
