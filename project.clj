(defproject approval "0.1.0-SNAPSHOT"
  :description "A module to grant access to a functionality via a token. Possible use cases: registration confirmation via email, password reset etc."
  :dependencies [[org.clojure/clojure "1.2.1"]
                 [ring/ring-core "0.3.11"]
                 [congomongo "0.1.4-SNAPSHOT"]]
  :dev-dependencies [[com.stuartsierra/lazytest "1.1.2"]
                     [swank-clojure "1.4.0-SNAPSHOT"]
                     [ring-mock "0.1.1"]])
