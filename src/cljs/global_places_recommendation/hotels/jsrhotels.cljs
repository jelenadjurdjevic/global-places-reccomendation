(ns global-places-recommendation.hotels.jshotels
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
  (if (= value "on")
      "yes"
      "no"))

(defn search-hotels
  "Change country XMLHttpReqest"
  []
  (let [xmlhttp (js/XMLHttpRequest.)]
	(aset xmlhttp "onreadystatechange" onready)
	(.open xmlhttp "POST" (str "/search-hotels?stars="(dom/value (dom/nodes (domcss/sel "select[id='stars']")))
							"&lprice="(dom/value (dom/by-id "lprice"))
							"&hprice="(dom/value (dom/by-id "hprice"))
							"&spa="(checkbox-value (dom/value (dom/by-id "spa")))
							"&pets="(checkbox-value (dom/value (dom/by-id "pets")))
							"&wifi="(checkbox-value (dom/value (dom/by-id "wifi")))
							"&parking="(checkbox-value (dom/value (dom/by-id "parking")))
) true)
	(.send xmlhttp)
))

(defn ^:export init []
  (if (and js/document
	   (.-getElementById js/document))
    (do (evts/listen! (dom/by-id "search")
		      :click
		      (fn [] (search-hotels)))
)))