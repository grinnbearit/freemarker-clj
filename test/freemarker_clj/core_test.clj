(ns freemarker-clj.core-test
  (:use freemarker-clj.core
        midje.sweet)
  (:import [freemarker.template Configuration]))



(facts
 (class (gen-config)) => Configuration)


(let [cfg (gen-config :shared {:a "Pie" :b "Apple"})]
  (facts
   (render cfg "test-templates/plain.ftl" {}) => "Hello World!\n"
   (render cfg "test-templates/basic_model.ftl" {:a "World" :b "Hello"}) => "Hello World!\n"
   (render cfg "test-templates/basic_model.ftl" {}) => "Apple Pie!\n"))
