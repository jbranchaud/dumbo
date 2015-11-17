(ns dumbo.repository
  (:require [clojure.java.jdbc :as sql]))

(def spec "postgresql://localhost:5432/dumbo")

(defn parse-int [s]
    (Integer/parseInt (re-find #"\A-?\d+" s)))

(defn table-exists?
  [table-name]
  (= 1
    (:count
      (first
        (sql/query spec ["select count(*) from information_schema.tables where table_name=?;" table-name])))))

(defn create-standard-tweets-table
  []
  (when-not (table-exists? "standard_tweets")
    (sql/execute! spec ["create table standard_tweets (
                           tweet_id bigint primary key,
                           tweeted_at timestamptz not null,
                           content text not null,
                           retweet boolean not null default false,
                           reply boolean not null default false );"])))

(defn create-gin-tweets-table
  []
  (when-not (table-exists? "gin_tweets")
    (sql/execute! spec ["create table gin_tweets (
                           tweet_id bigint primary key,
                           tweeted_at timestamptz not null,
                           content text not null,
                           retweet boolean not null default false,
                           reply boolean not null default false );
                         create index gin_tweet_content_idx
                           on gin_tweets
                           using gin(to_tsvector('english', content));"])))

(defn insert-tweet
  [tweet]
  (doseq [table-name ["standard_tweets" "gin_tweets"]]
    (sql/execute! spec [(str "insert into " table-name " (tweet_id, tweeted_at, content, retweet, reply) values (?::bigint,?::timestamptz,?,?,?);")
                        (get tweet 0)
                        (get tweet 3)
                        (get tweet 5)
                        (clojure.string/blank? (get tweet 6))
                        (clojure.string/blank? (get tweet 1))])))

(defn insert-tweets
  [tweet-data]
  (doseq [tweet tweet-data]
    (insert-tweet tweet)))

(defn create-tables
  []
  (sql/with-db-transaction [t-con spec]
    (create-standard-tweets-table)
    (create-gin-tweets-table)))
