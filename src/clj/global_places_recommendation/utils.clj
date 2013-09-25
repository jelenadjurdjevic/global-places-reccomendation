(ns global-places-recommendation.utils
  "Useful functions that are repeatedly called all over the project"
  (:import [java.io File])
  (:require [cljs.reader :refer [read-string]]
	    [global-places-recommendation.neo4j :as n4j]
	    [clojure.test :refer :all])
  (:refer-clojure :exclude [read-string]))

(with-test
(defn parse-integer
  "Parse string to integer"
  [s]
  (if (and (string? s) (re-matches #"\s*[+-]?\d+\s*" s))
    (read-string s)))
 (is (= 1 (parse-integer "1")))
 (is (= nil (parse-integer "1.5")))
 (is (= nil (parse-integer "1a")))
 (is (= 1 (parse-integer "01")))
 (is (= nil (parse-integer "integer")))
)

(with-test
(defn parse-double
  "Parse string to double"
  [s]
  (if (and (string? s) (re-matches #"\s*[+-]?\d+(\.\d+(M|M|N)?)?\s*" s))
    (read-string s)))
 (is (= 2 (parse-double "2")))
 (is (= 2.1 (parse-double "2.1")))
 (is (= 2.1 (parse-double "02.1")))
 (is (= nil (parse-double "02a.1")))
 (is (= nil (parse-double "double")))
 (is (= nil (parse-double "6.5E-5")))
 (is (= nil (parse-double "2.1.23")))
)

(with-test
(defn parse-number
  "Parse string to number"
  [x]
  (if (and (string? x) (re-matches #"^-?\d+\.?\d[E]?-?\d*$|^-?\d+\.?\d*$" x))
    (read-string x)))
 (is (= 2 (parse-number "2")))
 (is (= 2.1 (parse-number "2.1")))
 (is (= 2.1 (parse-number "02.1")))
 (is (= 6.5E-5 (parse-number "6.5E-5")))
 (is (= nil (parse-number "02a.1")))
 (is (= nil (parse-number "2.1.23")))
)

(with-test
(defn key-to-str
  "Change map key to be string key"
  [param-map param-pair]
  (assoc param-map (str (param-pair 0)) (param-pair 1)))
 (is (= {":param-key" "param value"} (key-to-str {} [:param-key "param value"])))
 (is (= {":param-key" 0} (key-to-str {} [:param-key 0])))
 (is (= {":param-key" "a"} (key-to-str {} [:param-key "a" 0])))
)

(with-test
(defn map-keys-to-str
  "Change map keys to be string keys"
  [req-params]
  (reduce key-to-str {} (into [] req-params)))
 (is (= {":param-key1" "a"
	 ":param-key2" "b"
	 ":param-key3" "c"} (map-keys-to-str  {:param-key1 "a"
					       :param-key2 "b"
					       :param-key3 "c"})))
 (is (= {":param-key1" 1
	 ":param-key2" 2.6
	 ":param-key3" "c"} (map-keys-to-str  {:param-key1 1
					       :param-key2 2.6
					       :param-key3 "c"})))
)

(defn create-rels-for-node
  "Create relationships between node and its target nodes with data"
  ([node-id params-map cy-query-result rel]
    (doseq [target-node-id cy-query-result]
      (n4j/create-relationship node-id
				(read-string (str target-node-id))
				rel
				{:mg (read-string (get params-map (str ":value"target-node-id)))})))
  ([node-id params-vec rel]
    (doseq [params-map params-vec]
      (n4j/create-relationship node-id
				(get params-map :id)
				rel
				{:grams (get params-map :grams)
				 :quantity (get params-map :quantity)}))))

(defn update-rels-for-node
  "Update relationships between node and its target nodes with data"
  ([rel-ids params-map]
    (doseq [rel-id rel-ids]
      (n4j/update-relationship (read-string (str (rel-id 0))) {:mg (read-string (get params-map (str ":value"(rel-id 0))))})))
  ([params-vec]
    (doseq [params-map params-vec]
      (n4j/update-relationship (get params-map :id)
				{:grams (get params-map :grams)
				 :quantity (get params-map :quantity)}))))

(defn delete-rels-for-node
  "Delete relationships by ids"
  [rel-ids]
  (n4j/delete-many-relationships rel-ids))

(with-test
(defn add-data-to-map
  "Add node data to map"
  [start-map node]
  (let [id (:id node)
	data (:data node)]
       (assoc start-map id data)))
 (is (= {1 {:data1 1
	    :data2 "2"}} (add-data-to-map {} {:id 1
					       :data {:data1 1
						      :data2 "2"}})))
)

(defn nodes-data-to-map
  "Format data from nodes to maps"
  [index-type]
  (let [nodes (n4j/read-all-nodes-type-of index-type)]
       (reduce add-data-to-map {} nodes)))

(defn file-delete
  "Delete file from file path"
  [file-path]
  (.delete (File. file-path)))

(def alphanumeric "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890.")

(defn get-random-str [length]
  (apply str (repeatedly length #(rand-nth alphanumeric))))