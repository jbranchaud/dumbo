(defproject dumbo "0.1.0-SNAPSHOT"
  :description "loads personal twitter data dump into a postgres database"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/java.jdbc "0.4.1"]
                 [org.postgresql/postgresql "9.4-1201-jdbc41"]
                 [org.clojure/data.csv "0.1.3"]]
  :main dumbo.core)
