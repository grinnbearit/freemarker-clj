(ns freemarker-clj.shim
  (:use [clojure.walk :only [postwalk]])
  (:import [freemarker.template
            TemplateMethodModelEx

            TemplateBooleanModel
            TemplateCollectionModel
            TemplateDateModel
            TemplateHashModelEx
            TemplateNumberModel
            TemplateScalarModel
            TemplateSequenceModel]))

(defprotocol IFtlToClj
  (ftl->clj [o] "Convert a Ftl object to its Clojure equivalent."))


(extend-protocol IFtlToClj
  TemplateBooleanModel
  (ftl->clj [x]
    (.getAsBoolean x))

  TemplateCollectionModel
  (ftl->clj [x]
    (let [iterator (.iterator x)]
      (loop [acc []]
        (if (.hasNext iterator)
          (recur (conj acc (ftl->clj (.next iterator))))
          acc))))

  TemplateDateModel
  (ftl->clj [x]
    (.getAsDate x))

  TemplateHashModelEx
  (ftl->clj [x]
    (zipmap (ftl->clj (.keys x))
            (ftl->clj (.values x))))

  TemplateNumberModel
  (ftl->clj [x]
    (.getAsNumber x))

  TemplateScalarModel
  (ftl->clj [x]
    (.getAsString x))

  TemplateSequenceModel
  (ftl->clj [x]
    (for [i (range (.size x))]
      (ftl->clj (.get x i))))

  Object
  (ftl->clj [x]
    (throw (IllegalArgumentException. (format "Don't know how to convert %s (class %s) to clj" x (class x))))))


(defn fn->method
  [f]
  (reify
    TemplateMethodModelEx
    (exec [_ args]
      (apply f (map ftl->clj args)))))


(defn map->model
  "Stringifies keys replacing hyphens with underscores,
 and replaces functions with template methods"
  [m]
  (let [stringify (fn [[k v]] (if (keyword? k) [(.replace (name k) "-" "_") v] [k v]))]
    (postwalk (fn [x] (cond (map? x) (into {} (map stringify x))
                            (fn? x) (fn->method x)
                            :else x))
              m)))
