plugins {
    `java-library`
    `maven-publish`
}

group = "com.tcoded.lightlibs"
version = "0.0.6"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains:annotations:23.0.0")
}

val enablePublishing: Boolean = project.findProperty("enablePublishing")?.toString()?.toBoolean() == true

if (enablePublishing) {

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                from(components["java"])

                // Optional: add additional metadata
//                pom {
//                    name.set("My Library")
//                    description.set("A simple library to publish to Reposilite")
//                    url.set("https://example.com")
//                }
            }
        }

        repositories {
            maven {
                name = "reposilite"
                url = uri("https://repo.tcoded.com/releases") // or /snapshots if applicable

                credentials {
                    username = project.findProperty("REPOSILITE_USER")?.toString()
                        ?: System.getenv("REPOSILITE_USER")
                                ?: error("REPOSILITE_USER property or environment variable is not set")
                    password = project.findProperty("REPOSILITE_PASS")?.toString()
                        ?: System.getenv("REPOSILITE_PASS")
                                ?: error("REPOSILITE_PASS property or environment variable is not set")
                }

                authentication {
                    create<BasicAuthentication>("basic")
                }
            }
        }
    }

    tasks.assemble {
        dependsOn(tasks.publishToMavenLocal)
    }
}