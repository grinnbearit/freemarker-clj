(ns freemarker-clj.core
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
  [cfg path model]
  (with-out-str
    (.process (.getTemplate cfg path)
              model
              *out*)))
