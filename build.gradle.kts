import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	java
	kotlin("jvm") version "1.3.71"
	id("nebula.release") version "15.2.0"
	id("maven-publish")
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

tasks {
	named("release").get().apply {
		// All checks were already made by Gradle Build workflow => no checks here
		dependsOn.removeIf { it is TaskProvider<*> && it.name == "build" }
		dependsOn(named("assemble").get())
	}
}

tasks {
	named("publish") {
		// We need create new version git tag before publishing to Maven Central
		dependsOn("release")
		mustRunAfter("release")
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
				val ossrhUsername: String? = System.getenv("OSSRH_USERNAME")
				val ossrhPassword: String? = System.getenv("OSSRH_PASSWORD")
				if (ossrhUsername.isNullOrBlank() || ossrhPassword.isNullOrBlank()) {
					throw IllegalArgumentException("Both OSSRH_USERNAME and OSSRH_PASSWORD environment variables must not be empty")
				}

				username = ossrhUsername
				password = ossrhPassword
			}
			url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
		}
	}
}

