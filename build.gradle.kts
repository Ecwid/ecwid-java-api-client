import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	java
	kotlin("jvm") version "1.3.71"
	id("nebula.release") version "15.2.0"
	id("maven-publish")
	signing
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(kotlin("stdlib-jdk8"))
	implementation(kotlin("reflect"))

	implementation("com.google.code.gson:gson:2.8.5")
	implementation("org.apache.httpcomponents:httpclient:4.5.6")

	testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.0")
	testImplementation("org.junit.jupiter:junit-jupiter-params:5.4.0")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.4.0")
}

configure<JavaPluginConvention> {
	sourceCompatibility = JavaVersion.VERSION_1_8
}

java {
	withSourcesJar()
	withJavadocJar()
}

tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test> {
	useJUnitPlatform()
	testLogging {
		events("passed", "skipped", "failed")
		showStandardStreams = true
	}
}

tasks.withType<Wrapper> {
	gradleVersion = "6.3"
}

val settingsProvider = SettingsProvider()

tasks {
	named("release").get().apply {
		// All checks were already made by Gradle Build workflow => no checks here
		dependsOn.removeIf { it is TaskProvider<*> && it.name == "build" }
		dependsOn(named("assemble").get())
		// Publish artifacts to Maven Central before pushing new git tag to repo
		dependsOn(named("publish").get())
	}
}

tasks.withType<Sign> {
	doFirst {
		settingsProvider.validateGPGSecrets()
	}
}

tasks.withType<PublishToMavenRepository> {
	doFirst {
		settingsProvider.validateOssrhCredentials()
	}
}

publishing {
	publications {
		create<MavenPublication>("mavenJava") {
			from(components["java"])
			groupId = "com.ecwid.apiclient"
			artifactId = "api-client"
			versionMapping {
				usage("java-api") {
					fromResolutionOf("runtimeClasspath")
				}
				usage("java-runtime") {
					fromResolutionResult()
				}
			}
			pom {
				name.set("Ecwid Rest API wrapper")
				description.set("Ecwid Rest API wrapper")
				url.set("https://github.com/Ecwid/ecwid-java-api-client")
				licenses {
					license {
						name.set("The Apache License, Version 2.0")
						url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
					}
				}
				developers {
					developer {
						id.set("vgv")
						name.set("Vasily Vasilkov")
						email.set("vgv@ecwid.com")
					}
				}
				scm {
					connection.set("scm:git:git@github.com:Ecwid/ecwid-java-api-client.git")
					developerConnection.set("scm:git:git@github.com:Ecwid/ecwid-java-api-client.git")
					url.set("https://github.com/Ecwid/ecwid-java-api-client.git")
				}
			}
		}
	}
	repositories {
		maven {
			credentials {
				username = settingsProvider.ossrhUsername
				password = settingsProvider.ossrhPassword
			}
			url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
		}
	}
}

signing {
	useInMemoryPgpKeys(settingsProvider.gpgSigningKey, settingsProvider.gpgSigningPassword)
	sign(publishing.publications["mavenJava"])
}

class SettingsProvider {

	val gpgSigningKey: String?
		get() = System.getenv(GPG_SIGNING_KEY_PROPERTY)

	val gpgSigningPassword: String?
		get() = System.getenv(GPG_SIGNING_PASSWORD_PROPERTY)

	val ossrhUsername: String?
		get() = System.getenv(OSSRH_USERNAME_PROPERTY)

	val ossrhPassword: String?
		get() = System.getenv(OSSRH_PASSWORD_PROPERTY)

	fun validateGPGSecrets() = require(
		value = !gpgSigningKey.isNullOrBlank() && !gpgSigningPassword.isNullOrBlank(),
		lazyMessage = { "Both $GPG_SIGNING_KEY_PROPERTY and $GPG_SIGNING_PASSWORD_PROPERTY environment variables must not be empty" }
	)

	fun validateOssrhCredentials() = require(
			value = !ossrhUsername.isNullOrBlank() && !ossrhPassword.isNullOrBlank(),
			lazyMessage = { "Both $OSSRH_USERNAME_PROPERTY and $OSSRH_PASSWORD_PROPERTY environment variables must not be empty" }
	)

	companion object {
		private const val GPG_SIGNING_KEY_PROPERTY = "GPG_SIGNING_KEY"
		private const val GPG_SIGNING_PASSWORD_PROPERTY = "GPG_SIGNING_PASSWORD"
		private const val OSSRH_USERNAME_PROPERTY = "OSSRH_USERNAME"
		private const val OSSRH_PASSWORD_PROPERTY = "OSSRH_PASSWORD"
	}

}
