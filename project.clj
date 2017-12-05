(defproject freemarker-clj "0.2.0-SNAPSHOT"
  :description "A wrapper for the freemarker template engine"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.freemarker/freemarker "2.3.27-incubating"]]
  :plugins [[lein-midje "3.2.1"]
            [lein-swank "1.4.5"]]
  :profiles {:dev {:resources-path "test-templates"
                   :dependencies [[midje "1.9.0"]]}})
