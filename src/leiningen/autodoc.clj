(ns leiningen.autodoc
  (:use [clojure.string :only [join]]
        [clojure.pprint :only [cl-format]]
        [leiningen.core.eval :only [sh]]
        [leiningen.core.classpath :only [get-classpath]]
        [leiningen.deps :only [deps]])
  (:import [java.io File]))

(def autodoc-project
  {:dependencies [['autodoc "1.1.1"]] ;; TODO: Automate this number
   :disable-deps-clean true
   :checksum-deps true
   :repositories [["clojars" {:url "http://clojars.org/repo/"
                              :snapshots false}]]})

(defn build-classpath
  "Build the right classpath for loading the process that includes autodoc
and the project and its dependencies."
  [project]
  (join java.io.File/pathSeparatorChar
        `(~@(get-classpath project)
          ~@(get-classpath autodoc-project))))

(defn map-to-arg-array
  "Take a keyword->value map and turn in into an array of arguments for the shell command
prefixing the keywords by --"
  [the-map]
  (mapcat identity
          (for [[key val] the-map] [(str "--" (name key)) (str val)])))

(defn autodoc
  "Build the autodoc for this project."
  [project & args]
  (let [args (map-to-arg-array (merge (select-keys project
                                                   [:name :description :root])
                                      (:autodoc project)
                                      `{:source-path ~(join ":" (:source-paths project))
                                        :load-classpath ~(join ":" (get-classpath project))}))
        classpath (join java.io.File/pathSeparatorChar (get-classpath autodoc-project))]
    (apply sh
           (or (System/getenv "JAVA_CMD") "java")
           "-cp" classpath
           "autodoc.autodoc"
           args)))
