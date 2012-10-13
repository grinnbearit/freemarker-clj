(defproject freemarker-clj "0.1.0"
  :description "A wrapper for the freemarker template engine"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.freemarker/freemarker "2.3.19"]]
  :plugins [[lein-midje "2.0.0-SNAPSHOT"]
            [lein-swank "1.4.4"]]
  :profiles {:dev {:resources-path "test-templates"
                   :dependencies [[midje "1.4.0"]
                                  [com.stuartsierra/lazytest "1.2.3"]]
                   :repositories {"stuart" "http://stuartsierra.com/maven2"}}})
