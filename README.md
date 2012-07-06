## freemarker-clj

A wrapper for the [freemarker template engine](http://freemarker.sourceforge.net/)

In its early stages, will add more features as I need them, suggestions and patches welcome

## Usage

Create a new template config

```clojure
(use 'freemarker-clj.core)
(defonce cfg (gen-config "templates"))
(render cfg "template.ftl" {:some-seq [1 2 3] :some-map {:a 4 :b 5} :some-fn inc})
```

`render` stringifies the map, replacing hyphens with underscores

You can additionally pass a default map to cfg which will be shared

```clojure
(defonce cfg (gen-config "templates" :shared {:a 1 :b 2}))
(render cfg "template.ftl" {:b 3}) ; a => 1, b => 3
```

## Contributors

* [Baishampayan Ghose](https://github.com/ghoseb)

## License

Copyright © 2012 Sidhant Godiwala (grinnbearit)

Distributed under the Eclipse Public License, the same as Clojure.
