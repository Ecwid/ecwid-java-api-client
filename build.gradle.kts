import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	java
	kotlin("jvm") version "1.3.10"
}

group = "com.ecwid.apiclient"
version = "1.0-SNAPSHOT"

repositories {
	mavenCentral()
}

dependencies {
	implementation(kotlin("stdlib-jdk8"))

	implementation("com.google.code.gson:gson:2.8.5")
	implementation("org.apache.httpcomponents:httpclient:4.5.6")

	testImplementation("junit", "junit", "4.12")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
	testImplementation("org.junit.jupiter:junit-jupiter-params:5.3.1")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")
}

configure<JavaPluginConvention> {
	sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test> {
	useJUnitPlatform()
	testLogging {
		events("passed", "skipped", "failed")
	}
}

tasks.withType<Wrapper> {
	gradleVersion = "4.8.10"
}
