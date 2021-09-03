# mongo-jackson-codec

A simple mongo codec to ease the object encode/decode for
the [java-mongo-driver](https://github.com/mongodb/mongo-java-driver) using
the [Jackson-Databind](https://github.com/FasterXML/jackson-databind) object mapper.

## Usage

Read the [example](https://github.com/Solotory/mongo-jackson-codec/tree/master/example) module to know how works.

## Repository [![](https://jitpack.io/v/Solotory/mongo-jackson-codec.svg)](https://jitpack.io/#Solotory/mongo-jackson-codec)

* Maven

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>

<dependency>
    <groupId>com.github.Solotory</groupId>
    <artifactId>mongo-jackson-codec</artifactId>
    <version>1.0.0</version>
</dependency>
```

* Gradle

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.Solotory:mongo-jackson-codec:1.0.0'
}
```