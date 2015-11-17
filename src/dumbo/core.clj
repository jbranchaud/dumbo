(ns dumbo.core
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]
            [dumbo.repository :as repo]))

(defn load-csv-data
  [filename]
  (with-open [in-file (io/reader filename)]
    (doall
      (csv/read-csv in-file))))

(defn without-header [csv-contents]
  (rest csv-contents))

(defn -main
  [csv-filename]
  (repo/insert-tweets
    (-> (load-csv-data csv-filename)
        (without-header))))
