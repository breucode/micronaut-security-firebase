import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
  id("java-library")
  id("io.micronaut.library") version "3.4.1"
  id("com.diffplug.spotless") version "6.7.2"
  id("com.github.ben-manes.versions") version "0.42.0"
  id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
  id("maven-publish")
  id("signing")
}

java { toolchain.languageVersion.set(JavaLanguageVersion.of(8)) }

micronaut { version("3.4.3") }

val pomDesc = "Validation of Firebase tokens in Micronaut Security"
val artifactName = "micronaut-security-firebase"
val artifactGroup = "de.breuco"

group = artifactGroup

val artifactVersion = "0.2.3"

version = artifactVersion

spotless {
  val ktfmtVersion = "0.37"
  java { googleJavaFormat().aosp() }
  kotlinGradle {
    target("*.gradle.kts")

    ktfmt(ktfmtVersion).googleStyle()
  }
}

fun isNonStable(version: String): Boolean {
  val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
  val regex = "^[0-9,.v-]+(-r)?$".toRegex()
  val isStable = stableKeyword || regex.matches(version)
  return isStable.not()
}

tasks.named("dependencyUpdates", DependencyUpdatesTask::class.java).configure {
  rejectVersionIf { isNonStable(candidate.version) }

  revision = "release"
  gradleReleaseChannel = "current"
}

tasks.wrapper { distributionType = Wrapper.DistributionType.ALL }

repositories { mavenCentral() }

dependencies {
  annotationProcessor("io.micronaut:micronaut-inject-java")

  implementation("io.micronaut.security:micronaut-security-jwt")
  implementation("io.micronaut.reactor:micronaut-reactor")

  implementation("com.google.firebase:firebase-admin:9.0.0")
}

nexusPublishing {
  repositories {
    sonatype {
      nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
      snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
    }
  }
}

val sourcesJar by
  tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Assembles sources JAR"
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
  }

val javadocJar by
  tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Assembles javadoc"
    archiveClassifier.set("javadoc")
    from(tasks.javadoc)
  }

publishing {
  publications {
    afterEvaluate {
      create<MavenPublication>("mavenJava") {
        groupId = artifactGroup
        artifactId = artifactName
        version = version
        from(components["java"])

        artifact(sourcesJar)
        artifact(javadocJar)

        pom {
          name.set(artifactName)
          description.set(pomDesc)
          url.set("https://github.com/breucode/micronaut-security-firebase")
          licenses {
            license {
              name.set("Apache License 2.0")
              url.set("https://github.com/breucode/micronaut-security-firebase/blob/main/LICENSE")
            }
          }
          developers {
            developer {
              id.set("breucode")
              name.set("Pascal Breuer")
              email.set("pbreuer@breuco.de")
            }
          }
          scm {
            url.set("https://github.com/breucode/micronaut-security-firebase/tree/main")
            connection.set("scm:git:github.com:breucode/micronaut-security-firebase.git")
            developerConnection.set(
              "scm:git:ssh://github.com:breucode/micronaut-security-firebase.git"
            )
          }
        }
      }
    }
  }
}

signing {
  val signingKey: String? by project
  val signingPassword: String? by project
  useInMemoryPgpKeys(signingKey, signingPassword)
  sign(publishing.publications)
}
