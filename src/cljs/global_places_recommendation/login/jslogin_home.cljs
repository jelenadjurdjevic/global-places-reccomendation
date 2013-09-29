(ns global-places-recommendation.login.jslogin-home
  (:require [domina :as dom]
	    [domina.events :as evts]
	    [domina.css :as domcss]))

(defn onready-country
  "Swap updated content from response with current"
  [content]
  (if (and (= (aget (aget content "currentTarget") "readyState") 4)
	   (= (aget (aget content "currentTarget") "status") 200))
      (if (not (= (aget (aget content "currentTarget") "responseText") ""))
		(dom/swap-content! (dom/by-id "city")
			 (aget (aget content "currentTarget") "responseText"))
	)
))

(defn change-country
  "Change country XMLHttpReqest"
  []
  (let [xmlhttp (js/XMLHttpRequest.)]
	(aset xmlhttp "onreadystatechange" onready-country)
	(.open xmlhttp "POST" (str "/change-country?country="(dom/value (dom/nodes (domcss/sel "select[id='country']")))
) true)
	(.send xmlhttp)
))

(defn onready-city
  "Swap updated content from response with current"
  [content]
  (if (and (= (aget (aget content "currentTarget") "readyState") 4)
	   (= (aget (aget content "currentTarget") "status") 200))
      (do (dom/destroy! (dom/by-class "help"))
	  (dom/append! (dom/by-id "error-msgs")
		       (str "<div class=\"help\">"(aget (aget content "currentTarget") "responseText")"</div>")))
))

(defn change-city
  "Change city XMLHttpReqest"
  []
  (let [xmlhttp (js/XMLHttpRequest.)]
	(aset xmlhttp "onreadystatechange" onready-city)
	(.open xmlhttp "POST" (str "/change-city?city="(dom/value (dom/nodes (domcss/sel "select[id='city']")))
) true)
	(.send xmlhttp)
))

(defn ^:export init []
  (if (and js/document
	   (.-getElementById js/document))
    (do (evts/listen! (dom/by-id "country")
		      :change
		      (fn [] (change-country)))
;	(evts/listen! (dom/by-id "city")
;		      :change
;		      (fn [] (hide-register-pop-up)))
)))