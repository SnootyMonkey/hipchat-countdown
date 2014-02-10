(defproject hipchat-countdown "0.1.0-SNAPSHOT"
  :description "Keep a HipChat room's topic updated with a countdown to a specific date"
  :url "https://github.com/belucid/hipchat-countdown"
  :license {:name "Mozilla Public License v2.0"
            :url "http://www.mozilla.org/MPL/2.0/"}
  
  :min-lein-version "2.3.4"

  :main hipchat-countdown.core

  :dependencies [
    [org.clojure/clojure "1.5.1"] ; Lisp on the JVM http://clojure.org/documentation
    [clj-time "0.6.0"] ; data & time library https://github.com/clj-time/clj-time
    [clj-http "0.7.9"] ; HTTP library https://github.com/dakrone/clj-http
    [cheshire "5.3.1"] ; JSON de/encoding https://github.com/dakrone/cheshire
  ]

  :aliases {
    "ancient" ["with-profile" "dev" "do" "ancient" ":allow-qualified," "ancient" ":plugins" ":allow-qualified"] ; check for out of date dependencies
  }

  :plugins [
    [lein-ancient "0.5.4"] ; Check for outdated dependencies https://github.com/xsc/lein-ancient
  ]

)