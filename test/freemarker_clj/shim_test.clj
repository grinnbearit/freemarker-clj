(ns freemarker-clj.shim-test
  (:require [clojure.string :as str])
  (:use freemarker-clj.shim
        freemarker-clj.core
        midje.sweet))

(defn pred-check
  [pred]
  (fn [x]
    (if (pred x)
      ""
      (throw (Exception. (str x " failed"))))))


(let [cfg (gen-config)]
  (fact "Test arguments and return values"
        (render cfg "test-templates/method_model.ftl"
                {"string" (fn->method (pred-check string?))
                 "number" (fn->method (pred-check number?))
                 "boolean" (fn->method (pred-check (comp (constantly true) boolean)))
                 "sequence" (fn->method (pred-check seq?))
                 "hash" (fn->method (pred-check map?))
                 "count" (fn->method count)

                 "return_string" (fn->method (constantly "apple"))
                 "return_number" (fn->method (constantly Math/PI))
                 "return_boolean" (fn->method (constantly false))
                 "return_sequence" (fn->method (constantly [1 2 3]))
                 "return_hash" (fn->method (constantly {"a" 1 "b" 2}))
                 "return_date" (fn->method (constantly (let [cal (java.util.Calendar/getInstance)]
                                                         (.set cal 1970 0 1 0 0 0)
                                                         (.getTime cal))))})
        => "\n3\n2\napple\n3.142\ncrazy\n2\n2\nJan 1, 1970 12:00:00 AM\n"))


(let [cfg (gen-config)]
  (fact "Test map->model conversion"
        (render cfg "test-templates/mapped_model.ftl"
                (map->model {:data {:string "banana"
                                    :number 5
                                    :boolean false
                                    :sequence [1 2 3]
                                    :hash {:a 1}
                                    :date (let [cal (java.util.Calendar/getInstance)]
                                            (.set cal 2000 0 1 0 0 0)
                                            (.getTime cal))
                                    :fn dec
                                    :with-hyphens "underscores"
                                    :nested [4 {:b 2} inc]}}))
        => "banana\n5\ncrazy\n2\n1\nJan 1, 2000 12:00:00 AM\n1\nunderscores\n2\n2\n"))
