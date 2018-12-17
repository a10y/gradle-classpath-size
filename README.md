# gradle-classpath-size

Have you ever wondered what's keeping your runtime classpaths so big?

This plugin adds a `classpathSize` for projects that apply the `java` plugin. It scans through the resolved `default` configuration and lists the transitive size for all top-level dependencies, ordered by size.

Example output provided here when applied on Apache Kafka:

```
open_source/apache/kafka ❯❯❯ ./gradlew core:classpathSize
> Configure project :
Building project 'core' with Scala version 2.11.12
Building project 'streams-scala' with Scala version 2.11.12

> Task :core:classpathSize
\___ com.typesafe.scala-logging:scala-logging_2.11:3.9.0: 10 MB
\___ org.scala-lang:scala-reflect:2.11.12: 10 MB
\___ org.scala-lang:scala-library:2.11.12: 5 MB
\___ org.apache.kafka:clients:2.0.0: 4 MB
\___ com.fasterxml.jackson.core:jackson-databind:2.9.6: 1 MB
\___ org.apache.zookeeper:zookeeper:3.4.13: 968 KB
\___ com.yammer.metrics:metrics-core:2.2.0: 123 KB
\___ com.101tec:zkclient:0.10: 116 KB
\___ net.sf.jopt-simple:jopt-simple:5.0.4: 78 KB
\___ org.slf4j:slf4j-api:1.7.25: 41 KB
```

Sizes are printed adaptively to the closest whole unit, and output is ordered by total size.

## Usage

TODO to setup publishing. Until then, add `classpath files('/PATH/TO/gradle-classpath-size.jar')` to the `buildscript.dependencies` block in your root `build.gradle`, then in the project you want to run the task, `apply: 'io.github.a10y.classpath-size'`
