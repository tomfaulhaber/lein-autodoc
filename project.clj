(defproject autodoc/lein-autodoc "1.1.2"
  :description "A leiningen plug-in for the autodoc documentation generator"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :profiles {:dev {:dependencies [[org.clojure/clojure "1.9.0"]
                                  [leiningen "2.8.1"]]}}
  :deploy-repositories [["releases" :clojars]])
