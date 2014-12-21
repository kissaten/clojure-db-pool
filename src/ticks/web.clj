(ns ticks.web
  (:require
    [clojure.java.jdbc :as jdbc]
    [ticks.db :as db]
    [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
    [compojure.handler :refer [site]]
    [compojure.route :as route]
    [clojure.java.io :as io]
    [ring.adapter.jetty :as jetty]
    [environ.core :refer [env]]))

(defn migrated? []
  (-> (jdbc/query db/spec
    [(str "select count(*) from information_schema.tables "
    "where table_name='ticks'")])
    first :count pos?))

(defn migrate []
  (when (not (migrated?))
  (print "Creating database structure...") (flush)
  (jdbc/db-do-commands db/spec
    (jdbc/create-table-ddl
      :ticks
      [:id :serial "PRIMARY KEY"]
      [:body :varchar "NOT NULL"]
      [:tick :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"]))
      (println " done")))

(defn tick []
  (jdbc/insert! db/spec :ticks [:body] ["hello"]))

(defroutes app
  (GET "*" []
    (tick)
    {:status 200
      :headers {"Content-Type" "text/plain"}
      :body (str "Ticks: " (first (jdbc/query db/spec ["select count(*) from ticks"])))}))

(defn -main [& [port]]
  (migrate)
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))

;; For interactive development:
;; (.stop server)
;; (def server (-main))
