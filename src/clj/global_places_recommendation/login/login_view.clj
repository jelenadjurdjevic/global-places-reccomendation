(ns global-places-recommendation.login.login-view
  (:use (sandbar stateful-session))
  (:require [global-places-recommendation.neo4j :as n4j]
	    [global-places-recommendation.html-generator :as hg]
	    [net.cgrand.enlive-html :as en]
        [global-places-recommendation.factual :as gpr-fact]))

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
  [:title] (en/content "Welcome")
   [:option.city] (en/clone-for [locality (gpr-fact/fetch-localities-of-region "al")]
        [:option.city] (comp (en/content (get locality "locality"))
                 (en/set-attr :value (get locality "locality"))
                 (en/remove-attr :class)))
  [:tr.result-item] (en/clone-for [result (gpr-fact/fetch-locality-of-type :restaurants-us "Hoover")]
          [:td] (en/content {:tag :a,
                 :attrs {:href (get result "website")},
				     :content (get result "website")}))
  [:div.script] (en/content {:tag :script,
			     :attrs {:src "http://localhost:3000/js/login.js"},
			     :content nil})
  [:div.script] (en/append {:tag :script,
			    :attrs nil,
			    :content "global_places_recommendation.login.jslogin_home.init();"}))

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
						
(en/deftemplate localities
  (en/html-resource "public/login/city-select.html")
  [country]
  [:option.city] (en/clone-for [locality (gpr-fact/fetch-localities-of-region country)]
				[:option.city] (comp (en/content (get locality "locality"))
						     (en/set-attr :value (get locality "locality"))
						     (en/remove-attr :class))))
		
(en/deftemplate locality-recommend
  (en/html-resource "public/login/city-recommend.html")
  [city]
  [:tr.result-item] (en/clone-for [result (gpr-fact/fetch-locality-of-type :restaurants-us city)]
          [:td] (en/content {:tag :a,
                 :attrs {:href (get result "website")},
                 :content (get result "website")})))
				 
(en/deftemplate edit-user
  (hg/build-html-page [{:temp-sel [:div.maincontent],
			:comp "public/login/register.html",
			:comp-sel [:div.edit-user]}])
  [node]
  [:title] (en/content "Edit user")
  [:input#name] (en/set-attr :value (:name (:data node)))
  [:input#surname] (en/set-attr :value (:surname (:data node)))
  [:input#email] (en/set-attr :value (:email (:data node)))
  [:input#username] (en/set-attr :value (:username (:data node)))
  [:input#password] (en/set-attr :value (:password (:data node)))
  [:input#age] (en/set-attr :value (:age (:data node)))
  [:input#city] (en/set-attr :value (:city (:data node)))
  [:input#country] (en/set-attr :value (:country (:data node)))
  [:input#gender-male] (if (= (:gender (:data node)) "Male")
			  (en/set-attr :checked "checked")
			  (en/set-attr :name "gender"))
  [:input#gender-female] (if (= (:gender (:data node)) "Female")
			    (en/set-attr :checked "checked")
			    (en/set-attr :name "gender"))
  [:div.script] (en/content {:tag :script,
			     :attrs {:src "http://localhost:3000/js/login.js"},
			     :content nil})
  [:div.script] (en/append {:tag :script,
			    :attrs nil,
			    :content "global_places_recommendation.login.jslogin_edit.init();"}))