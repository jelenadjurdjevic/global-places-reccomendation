(ns global-places-recommendation.login.login-validators
  (:require [valip.core :refer [validate]]
	    [valip.predicates :refer [present? matches email-address?]]))

(defn login-credential-errors
  "Credentials for login form"
  [params]
  (validate params
	    [:username present? "Username can't be empty."]
	    [:password present? "Password can't be empty."]))

(defn create-user-errors
  "Credentials for login form"
  [params]
  (validate params
	    [:name present? "Name can't be empty."]
	    [:surname present? "Surname can't be empty."]
	    [:email present? "Email can't be empty."]
	    [:email email-address? "Email not in valid format."]
	    [:password present? "Password can't be empty."]
	    [:age present? "Age can't be empty."]
	    [:city present? "City can't be empty."]
	    [:country present? "Country can't be empty."]
	    [:gender present? "Gender can't be empty."]))