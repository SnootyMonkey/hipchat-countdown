(defproject hipchat-countdown "0.1.0-SNAPSHOT"
  :description "Keep a HipChat room's topic updated with a countdown to a specific date"
  :url "https://github.com/belucid/hipchat-countdown"
  :license {:name "Mozilla Public License v2.0"
            :url "http://www.mozilla.org/MPL/2.0/"}
  
  :min-lein-version "2.5.0"

  :main hipchat-countdown.core

  :dependencies [
    [org.clojure/clojure "1.6.0"] ; Lisp on the JVM http://clojure.org/documentation
    [clj-time "0.9.0"] ; data & time library https://github.com/clj-time/clj-time
    [clj-http "1.1.2"] ; HTTP library https://github.com/dakrone/clj-http
    [cheshire "5.4.0"] ; JSON de/encoding https://github.com/dakrone/cheshire
  ]

  :aliases {
    "ancient" ["with-profile" "dev" "do" "ancient" ":allow-qualified," "ancient" ":plugins" ":allow-qualified"] ; check for out of date dependencies
  }

  :plugins [
    [lein-ancient "0.6.3"] ; Check for outdated dependencies https://github.com/xsc/lein-ancient
  ]

)