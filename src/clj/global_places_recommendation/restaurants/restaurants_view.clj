(ns global-places-recommendation.restaurants.restaurants-view
  (:require [global-places-recommendation.html-generator :as hg]
	    [global-places-recommendation.neo4j :as n4j]
	    [net.cgrand.enlive-html :as en]))


(en/deftemplate restaurants-form
  (hg/build-html-page [{:temp-sel [:div.maincontent],
			:comp "public/restaurants/restaurants-form.html",
			:comp-sel [:table.restaurants]}])
  []
  [:title] (en/content "Search for restaurants")
  [:div.script] (en/content {:tag :script,
			     :attrs {:src "http://localhost:3000/js/restaurant.js"},
			     :content nil})
  [:div.script] (en/append {:tag :script,
			    :attrs nil,
			    :content "global-places-recommendation.restaurant.jsrestaurant.init();"})
)
