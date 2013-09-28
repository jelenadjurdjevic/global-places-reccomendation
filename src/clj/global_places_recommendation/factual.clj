(ns global-places-recommendation.factual
  "The starting namespace for the project. This is the namespace that
  users will land in when they start a Clojure REPL. It exists to
  provide convenience functions like 'go' and 'dev-server'."
  (:require [factual.api :as fact]))

(defn findtable
  "Find"
  []
  (fact/fetch {:table
		:places
		:filters {"region" "CA"}
		:geo {:$circle {:$center [34.06018, -118.41835]
				:$meters 5000}}}))

(defn fetch-localities-of-region
  "Fetch first 20 localities from particular region,
   first 20 is allowed for read from factual.com"
  [region]
  (fact/fetch {:table :global :select "locality" :filters {:region region}}))

(defn fetch-locality-of-type
  "Fetch locality of particular type - restaurants, hotels"
  [place-type locality]
  (fact/fetch {:table place-type :filters {:locality locality} :limit 4}))