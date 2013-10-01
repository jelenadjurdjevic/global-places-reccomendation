(ns global-places-recommendation.restaurants.jsrestaurants
  (:require [domina :as dom]
	    [domina.events :as evts]
	    [domina.css :as domcss]))

(defn onready
  "Swap updated content from response with current"
  [content]
  (if (and (= (aget (aget content "currentTarget") "readyState") 4)
	   (= (aget (aget content "currentTarget") "status") 200))
      (if (not (= (aget (aget content "currentTarget") "responseText") ""))
		(dom/swap-content! (dom/by-class "results")
			 (aget (aget content "currentTarget") "responseText"))
	)
))

(defn checkbox-value
  "Return true or false for checkbox value"
  [value]
  (= value "on"))

(defn search-restaurants
  "Change country XMLHttpReqest"
  []
  (let [xmlhttp (js/XMLHttpRequest.)]
	(aset xmlhttp "onreadystatechange" onready)
	(.open xmlhttp "POST" (str "/search-restaurants?organic="(checkbox-value (dom/value (dom/by-id "food-organic")))
							"&vegetarian="(checkbox-value (dom/value (dom/by-id "food-vegetarian")))
							"&gluten-free="(checkbox-value (dom/value (dom/by-id "food-gluten-free")))
							"&kids-menu="(checkbox-value (dom/value (dom/by-id "kids-menu")))
							"&wifi="(checkbox-value (dom/value (dom/by-id "wifi")))
							"&price="(dom/value (dom/nodes (domcss/sel "select[id='price']")))
) true)
	(.send xmlhttp)
))

(defn ^:export init []
  (if (and js/document
	   (.-getElementById js/document))
    (do (evts/listen! (dom/by-id "search")
		      :click
		      (fn [] (search-restaurants)))
)))