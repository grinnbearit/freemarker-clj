(ns freemarker-clj.core
  (:use [freemarker-clj.shim :only [map->model]])
  (:import [freemarker.template
            Configuration
            DefaultObjectWrapper
            TemplateMethodModel]))


(defn gen-config
  [template-home]
  (doto (Configuration.)
    (.setDirectoryForTemplateLoading (java.io.File. template-home))
    (.setObjectWrapper (DefaultObjectWrapper.))))


(defn render
  [cfg path model & {:keys [map->model?] :or {map->model? true}}]
  (with-out-str
    (.process (.getTemplate cfg path)
              (if map->model?
                (map->model model)
                model)
              *out*)))
