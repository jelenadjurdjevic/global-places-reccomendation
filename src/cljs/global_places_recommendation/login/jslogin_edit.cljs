(ns global-places-recommendation.login.jslogin-edit
  (:require [domina :as dom]
	    [domina.events :as evts]
	    [domina.css :as domcss]
            [global-places-recommendation.login.login-validators :refer [login-credential-errors
									 create-user-errors]]))

(defn prepend-errors
  "Prepend error"
  [errors]
  (doseq [error errors]
	 (dom/prepend! (dom/by-id "error-msgs")
		       (str "<div class=\"help\">"error"</div>"))))

(defn validate-regiser-form
  "Validate form"
  []
  (do (dom/destroy! (dom/by-class "help"))
  (if-let [errors (create-user-errors {:name (dom/value (dom/by-id "name"))
					:surname (dom/value (dom/by-id "surname"))
					:email (dom/value (dom/by-id "email"))
					:username (dom/value (dom/by-id "username"))
					:password (dom/value (dom/by-id "password"))
					:age (dom/value (dom/by-id "age"))
					:city (dom/value (dom/by-id "city"))
					:country (dom/value (dom/by-id "country"))
					:gender (if (= (first (dom/nodes (domcss/sel "input[name='gender']:checked"))) nil)
						    ""
						    (dom/value (dom/nodes (domcss/sel "input[name='gender']:checked"))))})]
	(do (prepend-errors (:name errors))
	    (prepend-errors (:surname errors))
	    (prepend-errors (:email errors))
	    (prepend-errors (:username errors))
	    (prepend-errors (:password errors))
	    (prepend-errors (:age errors))
	    (prepend-errors (:city errors))
	    (prepend-errors (:country errors))
	    (prepend-errors (:gender errors))
	    false)
	true)))

(defn ^:export init []
  (if (and js/document
	   (.-getElementById js/document))
    (let [register-form (dom/by-id "register-form")]
	(set! (.-onsubmit register-form) validate-regiser-form)
)))