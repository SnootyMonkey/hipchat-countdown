(ns hipchat-countdown.core
  (:require [clojure.java.io :refer (writer)]
            [clj-time.core :refer (in-days interval after?)]
            [clj-time.local :refer (local-now)]
            [clj-time.format :refer (parse formatters unparse)]
            [clj-http.client :refer (put)]
            [cheshire.core :refer (generate-string)]))

(def base-url "https://api.hipchat.com/v2/room/")

(def now (local-now))
(def iso-format (formatters :date-time-no-ms))
(def now-stamp (unparse iso-format now))

(def config (read-string (slurp "config.edn")))

(def update-file ".last-update")

(def countdown-date (parse (:countdown-date config)))
(def days-from-now (if (after? countdown-date now)
                      (in-days (interval now countdown-date))
                      0))

(def hipchat-url (str base-url (:room config) "/topic?auth_token=" (:v2-api-token config)))

(def topic 
  (if (after? now countdown-date)
    (:final-msg config)
    (str (:msg-prefix config) days-from-now (:msg-suffix config))))

(def update-body (generate-string {:topic topic}))

(def last-update 
  (if (.exists (clojure.java.io/as-file update-file))
    (read-string (slurp update-file))
    "never"))

(defn- record-update []
  (with-open [wrtr (writer update-file)]
    (.write wrtr (pr-str now-stamp))))

(defn- update-needed? 
  "we need an update if:
    
    we've never updated
      -or-
    we're past the goal for the first clj-time
      -or-
    the days from now to the goal has changed since our last update"
  []
  (or 
    (= last-update "never")
    (and 
      (after? countdown-date (parse last-update))
      (or (after? now countdown-date)
          (not (= days-from-now (in-days (interval (parse last-update) countdown-date))))))))

(defn- post-update []
  (println "hipchat-countdown: updating!")
  (let [result (put hipchat-url {
    :body update-body
    :content-type :json
    :socket-timeout 10000 ; in miliseconds
    :connection-timeout 60000 ; in miliseconds
    :accept :json
    :throw-exceptions false})
    status (:status result)]
    (if (= 204 status)
      (println "hipchat-countdown: successful update!")
      (println "hipchat-countdown: update returned status -" status "\n" result))))
    
(defn -main
  "Update the HipChat room topic name if needed."
  []
  (println "hipchat-countdown: Started at -" now-stamp)
  (println "hipchat-countdown: Last room update at -" last-update)
  (if (update-needed?)
    (do 
      (record-update) ; do this first so we can't accidentally spam the room
      (post-update))
    (println "hipchat-countdown: No topic update required."))
  (println "hipchat-countdown: Complete"))