(ns leiningen.autodoc
  (:use [clojure.string :only [join]]
        [clojure.pprint :only [cl-format]]
        [leiningen.compile :only [sh]] ; in 2.0 this moves to leiningen.eval
        [leiningen.deps :only [deps]])
  (:import [java.io File]))

(def autodoc-project
  {:dependencies [['autodoc "0.9.0-SNAPSHOT"]]
   :disable-deps-clean true
   :checksum-deps true})

(defn autodoc-dir
  "Determine the directory in which to store the autodoc jar files that we pull
down from the repos. This is the autodoc/ subdirectory under lib for the project"
  [project]
  (File. (:library-path project) "autodoc"))

(defn get-autodoc-jars
  "Get the jars for running autodoc and put them in the directory autodoc/
under the library directory. This will be used for running the separate
autodoc process without corrupting anything in the project itself."
  [project]
  (let [autodoc-project (merge autodoc-project
                               {:library-path (.getPath (autodoc-dir project))})]
    (deps autodoc-project)))

(defn build-classpath
  "Build the right classpath for loading the process that includes autodoc
and the project and its dependencies."
  [project]
  (join java.io.File/pathSeparatorChar
        `(~@(filter #(.endsWith % ".jar")
                    (map #(.getPath %) (.listFiles (autodoc-dir project))))
          ~(:source-path project)
          ~@(filter #(.endsWith % ".jar")
                    (map #(.getPath %) (.listFiles (File. (:library-path project)))))
          ~@((juxt :compile-path :resources) project))))

(defn map-to-arg-array
  "Take a keyword->value map and turn in into an array of arguments for the shell command
prefixing the keywords by --"
  [the-map]
  (mapcat identity
          (for [[key val] the-map] [(str "--" (name key)) (str val)])))

(defn autodoc
  "Build the autodoc for this project."
  [project & args]
  (get-autodoc-jars project)
  (let [args (map-to-arg-array (merge (select-keys project
                                                   [:name :description :source-path :root])
                                      (:autodoc project)))
        classpath (build-classpath project)]
    (apply sh
           (or (System/getenv "JAVA_CMD") "java")
           "-cp" classpath
           "autodoc.autodoc"
           args)))
