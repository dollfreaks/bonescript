(defproject com.dollfreaks.bonescript "0.1.0-SNAPSHOT"
  :description "Clojurescript wrappers for the BeagleBone Bonescript library"
  :url "https://github.com/dollfreaks/bonescript"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/core.async "0.1.0-SNAPSHOT"]
                 [org.clojure/clojurescript "0.0-1859"]]

  :repositories {"sonatype" "https://oss.sonatype.org/content/groups/public/"
                 "sonatype-staging" "https://oss.sonatype.org/content/groups/staging/"}

  :plugins [[lein-cljsbuild "0.3.2"]]

  :cljsbuild {
              :builds [{:source-paths ["src"]
                        :compiler {:optimizations :simple
                                   :pretty-print true
                                   :target :nodejs}
                        }]})
