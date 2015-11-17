(ns clickbait-detector.templates
  (:use hiccup.core hiccup.page hiccup.form))


(def menu-items (list "UK" "Opinion" "Sport" "Life & Style" "Tech" "Travel"))

(def menu-items-html
  (interpose " | " (map #(html [:a %]) menu-items)))

(defn render-item [item]
  (let [{:keys [headline id]} item
        clickbait (if (:clickbait item)
                  "clickbait"
                  "")
        item-classes (str "item " clickbait)
        ]
  [:div {:class item-classes}
   [:p {:class "headline"} headline]
   [:aside {:class "section"} id]]))

(defn main-html [items]
  (html5
   [:head
    [:link {:href "/css/style.css" :rel "stylesheet"}]]
   [:body
    [:div {:class "wrapper"}
     [:header
      [:div {:class "header"}
       [:span {:class "header-main"} [:h2 "Guardian"]]
       [:span menu-items-html]]]
     [:div {:class "content"}
       (map render-item items)]
    [:script {:src "/js/compiled/clickbait-detector.js" :type "text/javascript"}]]))
