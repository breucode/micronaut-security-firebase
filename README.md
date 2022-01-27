[![GitHub release (latest by date)](https://img.shields.io/github/v/release/breucode/micronaut-security-firebase?style=flat-square)](https://github.com/breucode/micronaut-security-firebase/releases/latest)
[![Maven central release (latest)](https://img.shields.io/maven-central/v/de.breuco/micronaut-security-firebase?style=flat-square)](https://search.maven.org/artifact/de.breuco/micronaut-security-firebase)
[![License](https://img.shields.io/github/license/breucode/micronaut-security-firebase?style=flat-square)](LICENSE)

# micronaut-security-firebase
Integrates Firebase Auth Token validation into a Micronaut application via [Micronaut Security](https://micronaut-projects.github.io/micronaut-security/latest/guide/).

## Install and enable dependency

### Add and initialize Firebase Admin SDK

You need to initialize the Firebase SDK at application startup.

[Add Firebase Admin SDK dependency](https://firebase.google.com/docs/admin/setup#add-sdk)

[Initialize the SDK](https://firebase.google.com/docs/admin/setup#initialize-sdk)

---

This dependency is available at [Maven Central](https://search.maven.org/artifact/de.breuco/micronaut-security-firebase).

### Gradle
```kotlin
dependencies {
    ...
    implementation("de.breuco:micronaut-security-firebase:0.1.0")
    compileOnly("io.micronaut.security:micronaut-security")
    ...
}
```

### Maven
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project>
    ...
    <dependencies>
        <dependency>
            <groupId>de.breuco</groupId>
            <artifactId>micronaut-security-firebase</artifactId>
            <version>0.1.0</version>
        </dependency>
        <dependency>
            <groupId>io.micronaut.security</groupId>
            <artifactId>micronaut-security</artifactId>
            <scope>compile</scope>
        </dependency>
    </dependencies>
    ...
</project>
```
