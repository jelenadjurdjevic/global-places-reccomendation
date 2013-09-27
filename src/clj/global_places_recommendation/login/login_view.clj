(ns global-places-recommendation.login.login-view
  (:use (sandbar stateful-session))
  (:require [global-places-recommendation.neo4j :as n4j]
	    [global-places-recommendation.html-generator :as hg]
	    [net.cgrand.enlive-html :as en]
	    [global-places-recommendation.utils :refer [nodes-data-to-map]]))

(en/deftemplate login
  (hg/build-html-page [{:temp-sel [:div.topcontent],
			:comp "public/login/login.html",
			:comp-sel [:div.login]}])
  []
  [:title] (en/content "Login")
  [:body] (en/prepend {:tag :div,
		       :attrs {:class "heading"}
		       :content nil})
  [:body] (en/append {:tag :div,
		      :attrs {:class "script"}
		      :content nil})
  [:td#error-msgs] (if (= (session-get :login-try) nil)
			  (en/set-attr :id "error-msgs")
			  (en/content {:tag :div,
				       :attrs {:class "help"}
				       :content "Data not found"}))
  [:div.script] (en/content {:tag :script,
			     :attrs {:src "http://localhost:3000/js/login.js"},
			     :content nil})
  [:div.script] (en/append {:tag :script,
			    :attrs nil,
			    :content "global_places_recommendation.login.jslogin.init();"}))

(en/deftemplate home
  (hg/build-html-page [{:temp-sel [:div.maincontent],
			:comp "public/login/home.html",
			:comp-sel [:div.home]}])
  []
  [:title] (en/content "Welcome"))

(en/deftemplate page-not-found
  (hg/build-html-page [{:temp-sel [:div.topcontent],
			:comp "public/login/page-not-found.html",
			:comp-sel [:div.page-not-found]}])
  [param]
  [:title] (en/content param)
  [:div.page-not-found] (en/content {:tag :div,
				     :attrs nil,
				     :content param}
				    {:tag :div,
				     :attrs nil,
				     :content [{:tag :a,
						:attrs {:href "/login"},
						:content "back"}]}))