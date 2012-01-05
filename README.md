# An Autodoc plugin for Clojure

This plugin allows you to use Leiningen to create documentation for 
your project using the Autodoc tool,
the same tool that is used to generate Clojure's own API reference documentation.

For full documentation on how to use Autodoc and this plugin
see [http://tomfaulhaber.github.com/autodoc/](http://tomfaulhaber.github.com/autodoc/).


## Usage
As usual with Leiningen plugins, you can access the plugin in one of two ways:

1. Install the plugin on a per-system basis with "lein plugin install lein-autodoc 0.9.0"
2. Add "[lein-autodoc 0.9.0]" to the dev-dependencies in your project.clj.

Once installed, you can run "lein autodoc" to generate the documentation for your project.

## Weirdness
In order to remain independent of the dependencies of Leiningen (since Autodoc is 
really a big standalone program with a ton of its own dependencies), lein-autodoc will 
add a directory under your lib/ called "autodoc". This directory will be filled with 
Autodoc and its dependencies. lein-autodoc will then run Autodoc in a separate process
to create the documentation.

## License

Copyright (C) 2012 Tom Faulhaber

Distributed under the Eclipse Public License, the same as Clojure.
