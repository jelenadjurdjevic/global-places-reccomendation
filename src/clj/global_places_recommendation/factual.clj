(ns global-places-recommendation.factual
  "The starting namespace for the project. This is the namespace that
  users will land in when they start a Clojure REPL. It exists to
  provide convenience functions like 'go' and 'dev-server'."
  (:require [factual.api :as fact]))


(defn fetch-localities-of-region
  "Fetch first 20 localities from particular region,
   first 20 is allowed for read from factual.com"
  [region]
  (remove #(= % {}) (into [] (distinct (fact/fetch {:table :global :select "locality" :filters {:region region}})))))

(defn fetch-locality-of-type
  "Fetch locality of particular type - restaurants, hotels"
  [place-type locality]
  (remove #(= (get % "website") nil) (fact/fetch {:table place-type :filters {:locality locality} :limit 5})))

(defn recommend-restaurants
  ""
  [organic
   vegetarian
   gluten-free
   price
   kids-menu
   wifi]
  (remove #(= (get % "website") nil) (fact/fetch {:table :restaurants-us :filters {"options_organic" {:$eq organic}
                       "options_vegetarian" {:$eq vegetarian}
                       "options_glutenfree" {:$eq gluten-free}
                       "price" {:$eq price}
                       "kids_menu" {:$eq kids-menu}
                       "wifi" {:$eq wifi}} :limit 5})))