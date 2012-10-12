(ns freemarker-clj.core
  (:use [freemarker-clj.shim :only [map->model]]
        [clojure.java.io :only [file]])
  (:import [freemarker.template
            Configuration
            DefaultObjectWrapper
            TemplateMethodModel]
           [java.io StringWriter]))


(defn gen-config
  [& {:keys [shared] :or {shared {}}}]
  (let [cfg (doto (Configuration.)
              (.setObjectWrapper (DefaultObjectWrapper.)))]
    (doseq [[k v] (map->model shared)]
      (.setSharedVariable ^Configuration cfg ^String k v))
    cfg))


(defn render
  "Renders a template given by Configuration cfg and a path using model as input and writes it to out
If out is not provided, returns a string
If translate-model? is true, freemarker-clj.shim/map->model is run on the model
"
  ([^Configuration cfg path model]
     (str (render cfg (StringWriter.) path model)))
  ([^Configuration cfg out path model & {:keys [translate-model?] :or {translate-model? true}}]
     (.process (.getTemplate cfg path)
               (if translate-model?
                 (map->model model)
                 model)
               out)
     out))
