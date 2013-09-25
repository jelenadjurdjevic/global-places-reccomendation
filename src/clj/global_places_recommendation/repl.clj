(ns global-places-recommendation.repl
  "The starting namespace for the project. This is the namespace that
  users will land in when they start a Clojure REPL. It exists to
  provide convenience functions like 'go' and 'dev-server'."
  (:use [clojure.repl]
	[clojure.java.shell :only [sh]])
  (:require [clojure.java.browse :as browse]
	    [global-places-recommendation.server :as server]
	    [global-places-recommendation.neo4j :as n4j]
	    [global-places-recommendation.selenium.selenium-test :as sl]
	    [factual.api :as fact]))

(fact/factual! "dLdrD1DFyqEErPNYCWL6fUDfjOYRX91QGwidQHan" "4xzqiH0N87cgT1iMxG9MDdc1txGqkTovYVK1jxq4")

(defn findtable
  "Find"
  []
  (fact/fetch {:table
		:places
		:filters {"region" "CA"}
		:geo {:$circle {:$center [34.06018, -118.41835]
				:$meters 5000}}}))

(defn selenium-test
  "Selenium"
  []
  (sl/test-suite))

(defonce server (ref nil))

(defn start-server
 "Start the development server and open the host application in the
 default browser."
  []
 ; (if (= (str (:out (sh "neo4j-community/bin/neo4j" "status"))) "Neo4j Server is not running\n")
 ;     (println (:out (sh "neo4j-community/bin/neo4j" "start")))
 ; )
 ; (n4j/connect-neo4j)
  (dosync (ref-set server (server/run-server)))
  (future (Thread/sleep 3000)
          (browse/browse-url "http://localhost:3000/login")))

(defn restart-server
  "Restart server"
  []
  (.stop @server)
  (start-server))

(defn stop-server
  "Stop server"
  []
  (.stop @server)
  (if (= (re-find #"Neo4j Server is running at pid " (:out (sh "neo4j-community/bin/neo4j" "status"))) "Neo4j Server is running at pid ")
      (println (:out (sh "neo4j-community/bin/neo4j" "stop")))
  )
)

(defn -main [& args]
  (start-server))

(println)
(println "Type (start-server) to launch server.")
(println "Type (restart-server) to restart server.")
(println "Type (stop-server) to stop server.")
(println)
(println "Type (selenium-test) to test functionalities.")
(println)
