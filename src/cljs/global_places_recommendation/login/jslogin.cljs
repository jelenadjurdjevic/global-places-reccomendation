(ns global-places-recommendation.login.jslogin
  (:require [domina :as dom]
	    [domina.events :as evts]
	    [domina.css :as domcss]
            [global-places-recommendation.login.login-validators :refer [login-credential-errors]]))

(defn prepend-errors
  "Prepend error"
  [errors]
  (doseq [error errors]
	 (dom/prepend! (dom/by-id "error-msgs")
		       (str "<div class=\"help\">"error"</div>"))))

(defn validate-form
  "Validate form"
  []
  (do (dom/destroy! (dom/by-class "help"))
  (if-let [errors (login-credential-errors {:username (dom/value (dom/by-id "username"))
					    :password (dom/value (dom/by-id "password"))})]
	(do (prepend-errors (:username errors))
	    (prepend-errors (:password errors))
	    false)
	true)))

(defn onready
  "Swap updated content from response with current"
  [content]
  (if (and (= (aget (aget content "currentTarget") "readyState") 4)
	   (= (aget (aget content "currentTarget") "status") 200))
      (do (dom/destroy! (dom/by-class "help"))
	  (dom/append! (dom/by-id "error-msgs")
		       (str "<div class=\"help\">"(aget (aget content "currentTarget") "responseText")"</div>")))
))

(defn save-user
  "Save user XMLHttpReqest"
  []
  (let [xmlhttp (js/XMLHttpRequest.)]
	(aset xmlhttp "onreadystatechange" onready)
	(.open xmlhttp "PUT" (str "/save-user?name="(dom/value (dom/by-id "name"))
					    "&surname="(dom/value (dom/by-id "surname"))
					    "&email="(dom/value (dom/by-id "email"))
					    "&username="(dom/value (dom/by-id "username"))
					    "&password="(dom/value (dom/by-id "password"))
					    "&age="(dom/value (dom/by-id "age"))
					    "&city="(dom/value (dom/by-id "city"))
					    "&country="(dom/value (dom/by-id "country"))
;					    "&gender="(dom/value (dom/by-id "gender"))
) true)
	(.send xmlhttp)
))

(defn hide-register-pop-up
  ""
  []
  (let [selector (dom/by-class "register")]
	(if (= (re-find #"block" (dom/style selector "display")) "block")
	    (dom/set-style! selector "display" "none"))))

(defn ^:export init []
  (if (and js/document
	   (.-getElementById js/document))
    (let [login-form (dom/by-id "login-form")]
	(set! (.-onsubmit login-form) validate-form)
	(evts/listen! (dom/by-id "register")
		      :click
		      (fn []
			  (let [selector (dom/by-class "register")]
				(if (= (re-find #"none" (dom/style selector "display")) "none")
				    (dom/set-style! selector "display" "block")))))
	(evts/listen! (dom/by-id "exit")
		      :click
		      (fn [] (hide-register-pop-up)))
	(evts/listen! (dom/by-id "register-btn")
		      :click
		      (fn [] (do (hide-register-pop-up)
				 (js/alert (dom/nodes (domcss/sel "input[name='gender']:checked")))
				 (save-user))))
)))