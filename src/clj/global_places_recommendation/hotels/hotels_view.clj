(ns global-places-recommendation.hotels.hotels-view
  (:require [global-places-recommendation.html-generator :as hg]
	    [global-places-recommendation.neo4j :as n4j]
	    [net.cgrand.enlive-html :as en]
	    [global-places-recommendation.factual :as gpr-fact]))

(en/defsnippet hotels-table
  (en/html-resource "public/hotels/hotels-table.html")
  [:table.results]
  [])

(en/deftemplate hotels-form
  (hg/build-html-page [{:temp-sel [:div.maincontent],
			:comp "public/hotels/hotels-form.html",
			:comp-sel [:table.hotels]}])
  []
  [:title] (en/content "Search for hotels")
  [:td.search-results] (en/content (hotels-table))
  [:div.script] (en/content {:tag :script,
			     :attrs {:src "http://localhost:3000/js/hotels.js"},
			     :content nil})
  [:div.script] (en/append {:tag :script,
			    :attrs nil,
			    :content "global_places_recommendation.hotels.jshotels.init();"}))

(en/deftemplate hotels-result
  (en/html-resource "public/hotels/hotels-table.html")
  [req-params]
  [:tr.result-item] (en/clone-for [result (gpr-fact/recommend-hotels (:stars req-params)
									  (:lprice req-params)
									  (:hprice req-params)
									  (:spa req-params)
									  (:pets req-params)
									  (:parking req-params)
									  (:wifi req-params))]
				  [:td] (en/content {:tag :a,
						     :attrs {:href (get result "website")},
						     :content (get result "website")}))
)