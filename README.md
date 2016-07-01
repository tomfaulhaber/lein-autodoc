# An Autodoc plugin for Leiningen

This plugin allows you to use Leiningen to create documentation for
your project using the Autodoc tool,
the same tool that is used to generate Clojure's own API reference documentation.

For full documentation on how to use Autodoc and this plugin
see [http://tomfaulhaber.github.com/autodoc/](http://tomfaulhaber.github.com/autodoc/).


## Usage
As usual with Leiningen plugins, you can access the plugin in one of two ways:

1. Edit your leiningen profile (`~/.lein/profiles.clj`) to add the plugin. The option will look something like this:

```clj
{:user
 {:plugins [[autodoc/lein-autodoc "1.1.1"]]
  ...}
 :repl { ... }}
```

2. Add `[autodoc/lein-autodoc "1.1.1"]` to the `:plugins` in your project.clj.

Once installed, you can run `lein autodoc` to generate the documentation for your project. The output will be in the autodoc subdirectory of your project. Simply open `autodoc/index.html` to browse the documentation.

## License

Copyright (C) 2012-2016 Tom Faulhaber

Distributed under the Eclipse Public License, the same as Clojure.
