import com.adarshr.gradle.testlogger.theme.ThemeType
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension.Companion.DEFAULT_SRC_DIR_KOTLIN
import io.gitlab.arturbosch.detekt.extensions.DetektExtension.Companion.DEFAULT_TEST_SRC_DIR_KOTLIN
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	java
	signing
	kotlin("jvm") version "1.7.21"
	id("com.adarshr.test-logger") version "3.2.0"
	id("io.codearte.nexus-staging") version "0.30.0"
	id("nebula.release") version "17.1.0"
	id("maven-publish")
	id("io.gitlab.arturbosch.detekt") version "1.22.0"
	id("org.gradle.test-retry") version "1.4.1"
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(kotlin("stdlib-jdk8"))
	implementation(kotlin("reflect"))

	api("com.google.code.gson:gson:2.10")
	api("org.apache.httpcomponents:httpclient:4.5.13")

	testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
	testImplementation("org.reflections:reflections:0.10.2")
	testImplementation("uk.co.jemos.podam:podam:7.2.11.RELEASE")

	detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.22.0")
}

configure<JavaPluginConvention> {
	sourceCompatibility = JavaVersion.VERSION_11
}

java {
	withSourcesJar()
	withJavadocJar()
}

tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = "11"
}

tasks.withType<Test> {
	useJUnitPlatform()
	testlogger {
		showFullStackTraces = true
		showStandardStreams = true
		showPassedStandardStreams = false
		showSkippedStandardStreams = false
		showFailedStandardStreams = true
		slowThreshold = Consts.SLOW_TESTS_LOGGING_THRESHOLD_MS
		theme = ThemeType.STANDARD
	}
	retry {
		maxRetries.set(Consts.MAX_TEST_RETRIES_COUNT)
	}
}

tasks.withType<Detekt>().configureEach {
	jvmTarget = "11"
}

val settingsProvider = SettingsProvider()

tasks {
	// All checks were already made by workflow "On pull request" => no checks here
	if (gradle.startParameter.taskNames.contains("final")) {
		named("build").get().apply {
			dependsOn.removeIf { it == "check" }
		}
	}

	// Publish artifacts to Maven Central before pushing new git tag to repo
	named("release").get().apply {
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

tasks.register("printFinalReleaseNode") {
	doLast {
		printFinalReleaseNode(
			groupId = PublicationSettings.GROUP_ID,
			artifactId = PublicationSettings.ARTIFACT_ID,
			sanitizedVersion = project.sanitizeVersion()
		)
	}
}

tasks.register("printDevSnapshotReleaseNode") {
	doLast {
		printDevSnapshotReleaseNode(
			groupId = PublicationSettings.GROUP_ID,
			artifactId = PublicationSettings.ARTIFACT_ID,
			sanitizedVersion = project.sanitizeVersion()
		)
	}
}

detekt {
	allRules = false
	basePath = "$projectDir"
	buildUponDefaultConfig = true
	config = files("$projectDir/config/detekt.yml")
	input = files(
		"build.gradle.kts",
		DEFAULT_SRC_DIR_KOTLIN,
		DEFAULT_TEST_SRC_DIR_KOTLIN
	)
	parallel = true

	reports {
		html.enabled = true
		sarif.enabled = true
		txt.enabled = false
		xml.enabled = false
	}
}

publishing {
	publications {
		create<MavenPublication>("mavenJava") {
			from(components["java"])
			groupId = PublicationSettings.GROUP_ID
			artifactId = PublicationSettings.ARTIFACT_ID
			version = project.sanitizeVersion()
			versionMapping {
				usage("java-api") {
					fromResolutionOf("runtimeClasspath")
				}
				usage("java-runtime") {
					fromResolutionResult()
				}
			}
			pom {
				name.set(PublicationSettings.POM_NAME)
				description.set(PublicationSettings.POM_DESCRIPTION)
				url.set(PublicationSettings.POM_URL)
				licenses {
					license {
						name.set(PublicationSettings.LICENSE_NAME)
						url.set(PublicationSettings.LICENSE_URL)
					}
				}
				developers {
					developer {
						id.set(PublicationSettings.DEVELOPER_ID)
						name.set(PublicationSettings.DEVELOPER_NAME)
						email.set(PublicationSettings.DEVELOPER_EMAIL)
					}
				}
				scm {
					connection.set(PublicationSettings.SCM_CONNECTION)
					developerConnection.set(PublicationSettings.SCM_CONNECTION)
					url.set(PublicationSettings.SCM_URL)
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
			url = if (project.isSnapshotVersion()) {
				uri("https://oss.sonatype.org/content/repositories/snapshots/")
			} else {
				uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
			}
		}
	}
}

signing {
	useInMemoryPgpKeys(settingsProvider.gpgSigningKey, settingsProvider.gpgSigningPassword)
	sign(publishing.publications["mavenJava"])
}

nexusStaging {
	packageGroup = PublicationSettings.STAGING_PACKAGE_GROUP
	username = settingsProvider.ossrhUsername
	password = settingsProvider.ossrhPassword
}

// We want to change SNAPSHOT versions format from:
// 		<major>.<minor>.<patch>-dev.#+<branchname>.<hash> (local branch)
// 		<major>.<minor>.<patch>-dev.#+<hash> (github pull request)
// to:
// 		<major>.<minor>.<patch>-dev+<branchname>-SNAPSHOT
fun Project.sanitizeVersion(): String {
	val version = version.toString()
	return if (project.isSnapshotVersion()) {
		val githubHeadRef = settingsProvider.githubHeadRef
		if (githubHeadRef != null) {
			// github pull request
			version
				.replace(Regex("-dev\\.\\d+\\+[a-f0-9]+$"), "-dev+$githubHeadRef-SNAPSHOT")
		} else {
			// local branch
			version
				.replace(Regex("-dev\\.\\d+\\+"), "-dev+")
				.replace(Regex("\\.[a-f0-9]+$"), "-SNAPSHOT")
		}
	} else {
		version
	}
}

fun Project.isSnapshotVersion() = version.toString().contains("-dev.")

fun printFinalReleaseNode(groupId: String, artifactId: String, sanitizedVersion: String) {
	println()
	println("========================================================")
	println()
	println("New RELEASE artifact version were published:")
	println("	groupId: $groupId")
	println("	artifactId: $artifactId")
	println("	version: $sanitizedVersion")
	println()
	println("Discover on Maven Central:")
	println("	https://repo1.maven.org/maven2/${groupId.replace('.', '/')}/$artifactId/")
	println()
	println("Edit or delete artifacts on OSS Nexus Repository Manager:")
	println("	https://oss.sonatype.org/#nexus-search;gav~$groupId~~~~")
	println()
	println("Control staging repositories on OSS Nexus Repository Manager:")
	println("	https://oss.sonatype.org/#stagingRepositories")
	println()
	println("========================================================")
	println()
}

fun printDevSnapshotReleaseNode(groupId: String, artifactId: String, sanitizedVersion: String) {
	println()
	println("========================================================")
	println()
	println("New developer SNAPSHOT artifact version were published:")
	println("	groupId: $groupId")
	println("	artifactId: $artifactId")
	println("	version: $sanitizedVersion")
	println()
	println("Discover on Maven Central:")
	println("	https://oss.sonatype.org/content/groups/public/${groupId.replace('.', '/')}/$artifactId/")
	println()
	println("Edit or delete artifacts on OSS Nexus Repository Manager:")
	println("	https://oss.sonatype.org/#nexus-search;gav~$groupId~~~~")
	println()
	println("========================================================")
	println()
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

	val githubHeadRef: String?
		get() = System.getenv(GITHUB_HEAD_REF_PROPERTY)

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
		private const val GITHUB_HEAD_REF_PROPERTY = "GITHUB_HEAD_REF"
	}
}

object PublicationSettings {

	const val GROUP_ID = "com.ecwid.apiclient"
	const val ARTIFACT_ID = "api-client"

	const val POM_NAME = "Ecwid Rest API wrapper"
	const val POM_DESCRIPTION = "Ecwid Rest API wrapper"
	const val POM_URL = "https://github.com/Ecwid/ecwid-java-api-client"

	const val DEVELOPER_ID = "opensource-bot"
	const val DEVELOPER_NAME = "Ecwid Opensource Bot"
	const val DEVELOPER_EMAIL = "opensource-bot@ecwid.com"

	const val LICENSE_NAME = "The Apache License, Version 2.0"
	const val LICENSE_URL = "https://www.apache.org/licenses/LICENSE-2.0.txt"

	const val SCM_CONNECTION = "scm:git:git@github.com:Ecwid/ecwid-java-api-client.git"
	const val SCM_URL = "https://github.com/Ecwid/ecwid-java-api-client.git"

	const val STAGING_PACKAGE_GROUP = "com.ecwid"
}

object Consts {
	const val SLOW_TESTS_LOGGING_THRESHOLD_MS = 30_000L
	const val MAX_TEST_RETRIES_COUNT = 3
}
