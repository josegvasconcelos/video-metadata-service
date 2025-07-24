plugins {
    id("java")
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("jvm") version "1.9.24"
    id("org.flywaydb.flyway") version "9.22.3"
}

group = "com.josegvasconcelos"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_21

repositories {
    mavenCentral()
}

dependencies {
    // Spring Dependencies
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Database Dependencies
    implementation("org.postgresql:postgresql")
    implementation("org.flywaydb:flyway-core")

    // JWT Dependencies
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // MISC Dependencies
    implementation("de.huxhorn.sulky:de.huxhorn.sulky.ulid:8.2.0")
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    // Test Dependencies
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}