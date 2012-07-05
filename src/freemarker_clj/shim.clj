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


(defn ftl->clj
  [x]
  (cond
   (instance? TemplateBooleanModel x)
   (.getAsBoolean x)

   (instance? TemplateCollectionModel x)
   (let [iterator (.iterator x)]
     (loop [acc []]
       (if (.hasNext iterator)
         (conj acc (ftl->clj (.next iterator)))
         acc)))

   (instance? TemplateDateModel x)
   (.getAsDate x)

   (instance? TemplateHashModelEx x)
   (zipmap (ftl->clj (.keys x))
           (ftl->clj (.values x)))

   (instance? TemplateNumberModel x)
   (.getAsNumber x)

   (instance? TemplateScalarModel x)
   (.getAsString x)

   (instance? TemplateSequenceModel x)
   (for [i (range (.size x))]
     (ftl->clj (.get x i)))

   :else
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
