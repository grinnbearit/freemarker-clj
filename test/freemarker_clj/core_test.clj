(ns freemarker-clj.core-test
  (:use freemarker-clj.core
        midje.sweet)
  (:import [freemarker.template Configuration]))



(facts
 (class (gen-config "test-templates")) => Configuration)


(let [cfg (gen-config "test-templates")]
  (facts
   (render cfg "plain.ftl" {}) => "Hello World!\n"
   (render cfg "basic_model.ftl" {:a "World" :b "Hello"}) => "Hello World!\n"
   (render cfg "basic_model.ftl" {"a" "World" "b" "Hello"} :map->model? false) => "Hello World!\n"))
