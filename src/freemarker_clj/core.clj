(ns freemarker-clj.core
  (:use [freemarker-clj.shim :only [map->model]]
        [clojure.java.io :only [file]])
  (:import [freemarker.template
            Configuration
            DefaultObjectWrapper
            TemplateMethodModel]))


(defn gen-config
  [& {:keys [shared] :or {shared {}}}]
  (let [cfg (doto (Configuration.)
              (.setObjectWrapper (DefaultObjectWrapper.)))]
    (doseq [[k v] (map->model shared)]
      (.setSharedVariable ^Configuration cfg ^String k v))
    cfg))


(defn render
  [^Configuration cfg path model & {:keys [map->model?] :or {map->model? true}}]
  (with-out-str
    (.process (.getTemplate cfg path)
              (if map->model?
                (map->model model)
                model)
              *out*)))
