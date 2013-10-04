(ns global-places-recommendation.restaurants.restaurants-view
  (:require [global-places-recommendation.html-generator :as hg]
	    [global-places-recommendation.neo4j :as n4j]
	    [net.cgrand.enlive-html :as en]
      [global-places-recommendation.factual :as gpr-fact]))

(en/defsnippet restaurants-table
  (en/html-resource "public/restaurants/restaurants-table.html")
  [:table.results]
  [])


(en/deftemplate restaurants-form
  (hg/build-html-page [{:temp-sel [:div.maincontent],
			:comp "public/restaurants/restaurants-form.html",
			:comp-sel [:table.restaurants]}])
  []
  [:title] (en/content "Search for restaurants")
  [:td.search-results] (en/content (restaurants-table))
  [:div.script] (en/content {:tag :script,
			     :attrs {:src "http://localhost:3000/js/restaurants.js"},
			     :content nil})
  [:div.script] (en/append {:tag :script,
			    :attrs nil,
			    :content "global_places_recommendation.restaurants.jsrestaurants.init();"}))
(def ite 0)

(en/defsnippet suggest-hotels
  (en/html-resource "public/restaurants/restaurants-table.html")
  [:table.results]
  [lat
   lon]
  [:table.results] (en/set-attr :style "display:none" :class (str "suggest" ite))
  [:tr.result-item] (en/clone-for [result (gpr-fact/suggest-hotels lat lon)]
			[:td] (en/content {:tag :a,
					   :attrs {:href (get result "website")},
					   :content (get result "website")})))
				
(en/deftemplate restaurants-result
  (en/html-resource "public/restaurants/restaurants-table.html")
  [req-params]
  [:tr.result-item] (en/clone-for [result (gpr-fact/recommend-restaurants (:organic req-params)
                    (:vegetarian req-params)
                    (:gluten-free req-params)
                    (:price req-params)
                    (:kids-menu req-params)
                    (:wifi req-params))]
          [:td] (en/content {:tag :div,
                 :attrs nil,
                 :content [{:tag :a,
                :attrs {:href (get result "website")},
                :content (get result "website")}
                     {:tag :input,
                :attrs {:type "button"
                  :value "suggest hotels"
                  :id (str "suggest" (do (def ite (+ ite 1))
                        ite))}
                :content nil}
                     (first (suggest-hotels (get result "latitude")
                         (get result "longitude")))]}
))
)