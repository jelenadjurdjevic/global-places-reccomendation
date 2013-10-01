(defproject global-places-recommendation "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  ;; source code path
  :source-paths ["src/clj"]
  :test-paths ["src/clj"]
  :resource-paths ["resources"]

  :dependencies [[org.clojure/clojure "1.5.1"]
		 [compojure "1.1.5"]
		 [com.cemerick/valip "0.3.2"]
		 [clojurewerkz/neocons "1.1.0"]
		 [ring/ring-core "1.2.0"]
		 [ring/ring-jetty-adapter "1.1.0"]
		 [enlive "1.1.1"]
		 [domina "1.0.2-SNAPSHOT"]
		 [sandbar "0.4.0-SNAPSHOT"]
		 [org.clojure/data.json "0.2.2"]
		 [xml-apis/xml-apis "1.4.01"]
		 [clj-webdriver "0.6.0"]
		 [factual/factual-clojure-driver "1.5.1"]]
  ;:plugins [[lein2-eclipse "2.0.0"]]

  :main global-places-recommendation.repl

  :repl-init global-places-recommendation.repl

  :plugins [;; cljsbuild plugin
	    [lein-cljsbuild "0.3.2"]

	    ;; ring plugin
	    [lein-ring "0.8.5"]

	    ;; codox plugin
	    [codox "0.6.4"]]

  ;; cljsbuild options configuration
  :cljsbuild {:crossovers [valip.core 
                           valip.predicates
			   global-places-recommendation.login.login-validators]
	      :builds
		{:dev-login
		 {;; CLJS source code path
		  :source-paths ["src/brepl"
				 "src/cljs/global_places_recommendation/login"
				 "src/cljs/global_places_recommendation/utils"]

		  ;; Google Closure (CLS) options configuration
		  :compiler {;; CLS generated JS script filename
			     :output-to "resources/public/js/login.js"

			     ;; minimal JS optimization directive
			     :optimizations :whitespace

			     ;; generated JS code prettyfication
			     :pretty-print true}}
		 :prod-login
		 {;; CLJS source code path
		  :source-paths ["src/cljs/global_places_recommendation/login"
				 "src/cljs/global_places_recommendation/utils"]

		  ;; Google Closure (CLS) options configuration
		  :compiler {;; CLS generated JS script filename
			     :output-to "resources/public/js/login.js"

			     ;; minimal JS optimization directive
			     :optimizations :advanced}}
		 :dev-restaurants
		 {;; CLJS source code path
		  :source-paths ["src/brepl"
				 "src/cljs/global_places_recommendation/restaurants"
				 "src/cljs/global_places_recommendation/utils"]

		  ;; Google Closure (CLS) options configuration
		  :compiler {;; CLS generated JS script filename
			     :output-to "resources/public/js/restaurants.js"

			     ;; minimal JS optimization directive
			     :optimizations :whitespace

			     ;; generated JS code prettyfication
			     :pretty-print true}}
		 :prod-restaurants
		 {;; CLJS source code path
		  :source-paths ["src/cljs/global_places_recommendation/restaurants"
				 "src/cljs/global_places_recommendation/utils"]

		  ;; Google Closure (CLS) options configuration
		  :compiler {;; CLS generated JS script filename
			     :output-to "resources/public/js/restaurants.js"

			       ;; minimal JS optimization directive
					:optimizations :advanced}}
		:dev-hotels
		{;; CLJS source code path
			:source-paths ["src/brepl"
			"src/cljs/global_places_recommendation/hotels"]

		;; Google Closure (CLS) options configuration
		:compiler {;; CLS generated JS script filename
           :output-to "resources/public/js/hotels.js"

           ;; minimal JS optimization directive
           :optimizations :whitespace

           ;; generated JS code prettyfication
           :pretty-print true}}
		:prod-hotels
		{;; CLJS source code path
		:source-paths ["src/cljs/global_places_recommendation/hotels"]

		;; Google Closure (CLS) options configuration
		:compiler {;; CLS generated JS script filename
           :output-to "resources/public/js/hotels.js"

           ;; minimal JS optimization directive
           :optimizations :advanced}}}})