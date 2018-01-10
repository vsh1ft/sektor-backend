import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	application
	id("org.jetbrains.kotlin.jvm") version "1.1.61"
	id ("com.github.johnrengelman.shadow") version "2.0.1"
	id("io.spring.dependency-management") version "1.0.3.RELEASE"
	id("org.junit.platform.gradle.plugin") version "1.0.2"
}

repositories {
	mavenLocal()
	mavenCentral()
	maven("https://repo.spring.io/milestone")
}

application {
	mainClassName = "lt.tlistas.loginn.backend.ApplicationKt"
}

tasks {
	withType<KotlinCompile> {
		kotlinOptions {
			jvmTarget = "1.8"
			freeCompilerArgs = listOf("-Xjsr305=strict")
		}
	}
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.boot:spring-boot-dependencies:2.0.0.M7")
	}
}

dependencies {
	compile("lt.tlistas:core:1.1.1")
	compile("org.jetbrains.kotlin:kotlin-stdlib-jre8")
	compile("org.jetbrains.kotlin:kotlin-reflect")

	compile("org.springframework:spring-webflux")
	compile("org.springframework:spring-context") {
		exclude(module = "spring-aop")
	}
	compile("io.projectreactor.ipc:reactor-netty")

	compile("org.slf4j:slf4j-api")
	compile("ch.qos.logback:logback-classic")

	compile("com.fasterxml.jackson.module:jackson-module-kotlin")
	compile("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

	testCompile("io.projectreactor:reactor-test")

	testCompile("org.junit.jupiter:junit-jupiter-api")
	testRuntime("org.junit.jupiter:junit-jupiter-engine")
}
