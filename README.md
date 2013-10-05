global-places-reccomendation
============================
Global places reccomendation is an application which should be used as a guide when visiting the USA. 
User should register and log in to search places in us.
For different cities in different states in the US, it provides data about restaurants and hotels, according to the user's desire.
Also, when user pick some restaurant, application should provide reccomendation for hotel for this user. 

Instructions
============
Clone project with command "git clone https://github.com/jelenadjurdjevic/global-places-reccomendation" or using Git GUI
Download Neo4J server from http://www.neo4j.org/ and start server
From project root in terminal run command "lein run" or "lein repl" and in repl run command "(start-server)" to start application
Recommendation: before lein repl for application start, first build javascript files with command: "lein cljsbuild once dev-hotels", "lein cljsbuild once dev-restaurants"

Leiningen
=========
Version 2.0
Leiningen is a tool for building of code, written in Clojure. 
Leiningen is much simpler comparing with Maven and allows to define project's configuration using Clojure. 
Leiningen uses external tools and libraries to resolve dependencies and build a code, so it's pretty small.
http://leiningen.org/

Clojure
=======
dependency - [org.clojure/clojure "1.5.1"]
Clojure is a functional general-purpose language. 
Its focus on programming with immutable values and explicit progression-of-time constructs are intended to facilitate the development of more robust programs, particularly multithreaded ones.
http://clojure.org/

Factual API
===========
dependency - [factual/factual-clojure-driver "1.5.1"]
Global Places Data
Definitive database of over 65 million local business and point of interest listings in 50 countries.
http://www.factual.com/

Ring
====
dependency - [ring/ring-jetty-adapter "1.1.0"] [ring/ring-core "1.2.0"]
Ring is a library with capabilities to provide a reasonably pleasant HTTP interface.
Ring is also a Clojure wrapper around the Java HTTP Servlet API.
https://github.com/ring-clojure/ring

Compojure
=========
dependency - [compojure "1.1.5"]
Compojure is a small routing library for Ring that allows web applications to be composed of small, independent parts.
https://github.com/weavejester/compojure

Clojure Script
==============
plugin - [lein-cljsbuild "0.3.2"]
ClojureScript is a new compiler for Clojure that targets JavaScript. 
It is designed to emit JavaScript code which is compatible with the advanced compilation mode of the Google Closure optimizing compiler.
Command to build js files:

generates js development files that connects to brepl (browser repl) ->
lein cljsbuild once dev-restaurants dev-hotels

run brepl ->
lein trampoline cljsbuild repl-listen

generates js production file ->
lein cljsbuild once dev-restaurants dev-hotels

https://github.com/clojure/clojurescript

Neo4J
=====
Neo4j is a robust (fully ACID) transactional property graph database. 
Due to its graph data model, Neo4j is highly agile and blazing fast. 
For connected data operations, Neo4j runs a thousand times faster than relational databases.

http://www.neo4j.org/

Enlive
======
Enlive is a selector-based (à la CSS) templating library for Clojure.

https://github.com/cgrand/enlive

Valip
=====
dependency - [com.cemerick/valip "0.3.2"]
Valip is a validation library for Clojure. It is primarily designed to validate keyword-string maps, such as one might get from a HTML form.

https://github.com/cemerick/valip

Domina
======
dependency - [domina "1.0.2-SNAPSHOT"]
Domina is a jQuery inspired DOM manipulation library for ClojureScript. 
It provides a functional, idiomatic Clojure interface to the DOM manipulation facilities provided by the Google Closure library.

https://github.com/levand/domina

Sandbar
=======
dependency - [sandbar "0.4.0-SNAPSHOT"]
Sandbar is a web application library which is designed to be used with Compojure and/or Ring.
It builds on these projects providing the following additional features: Session and flash as a global map,
Authorization and authentication, including built-in support for form-based authentication, 
Forms and form validation

https://github.com/brentonashworth/sandbar