# freemarker-clj

A wrapper for the [freemarker template engine](http://freemarker.sourceforge.net/)

In its early stages, will add more features as I need them, suggestions and patches welcome

## Dependency

freemarker-clj is deployed on [clojars](https://clojars.org/freemarker-clj)

### leiningen

```clojure
[freemarker-clj "0.2.0-SNAPSHOT"]
```

### maven

```xml
<dependency>
  <groupId>freemarker-clj</groupId>
  <artifactId>freemarker-clj</artifactId>
  <version>0.2.0-SNAPSHOT</version>
</dependency>
```

## Usage

Create a new template config

```clojure
(use 'freemarker-clj.core)
(defonce cfg (gen-config))
(render cfg "template.ftl" {:some-seq [1 2 3] :some-map {:a 4 :b 5} :some-fn inc})
```

`render` stringifies the map, replacing hyphens with underscores

You can additionally pass a shared map to the template config

```clojure
(defonce cfg (gen-config :shared {:a 1 :b 2}))
(render cfg "template.ftl" {:b 3}) ; a => 1, b => 3
```

## Contributors

* [Baishampayan Ghose](https://github.com/ghoseb)
* [Jorge Urdaneta](https://github.com/jorgeu)
* [Peter Monks](https://github.com/pmonks)

## License

Copyright © 2012-2017 Sidhant Godiwala (grinnbearit)

Distributed under the Eclipse Public License, the same as Clojure.
