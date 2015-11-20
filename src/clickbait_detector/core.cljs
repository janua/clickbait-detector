(ns ^:figwheel-always clickbait-detector.core
  (:require [reagent.core :as r]
            [cljs.reader :refer [read-string]]
            [goog.dom :as dom]))

(enable-console-print!)

(defonce data (read-string (.-innerHTML (dom/getElement "data"))))

(defonce show (r/atom true))

(defn clickbait-classes [item]
  (if
    (:clickbait item)
    "clickbait"
    (if
      (true? @show)
      "noclickbait"
      "noclickbait noshow")))

(defn item [item]
  (let [{:keys [headline id]} item
        clickbait-style (clickbait-classes item)
        item-classes (str "item " clickbait-style)]
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
