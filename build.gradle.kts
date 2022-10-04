import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.3"
    id("io.spring.dependency-management") version "1.0.13.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
    kotlin("kapt") version "1.6.21"
    idea
}

apply(plugin = "kotlin-kapt")

group = "com.info"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("org.springframework.data.redis.core.RedisHash")
    annotation("org.springframework.data.mongodb.core.mapping.Document")
    annotation("javax.persistence.Embeddable")
}

noArg {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("org.springframework.data.redis.core.RedisHash")
    annotation("org.springframework.data.mongodb.core.mapping.Document")
    annotation("javax.persistence.Embeddable")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-starter")
    //WebStarter
    implementation("org.springframework.boot:spring-boot-starter-web"){
        exclude(module = "spring-boot-starter-tomcat")
    }
    //undertow
    implementation("org.springframework.boot:spring-boot-starter-undertow")
    //batch
    implementation("org.springframework.boot:spring-boot-starter-batch")
    //Mysql
    runtimeOnly("mysql:mysql-connector-java:8.0.20")
//    //Komoran
//    implementation("com.github.shin285:KOMORAN:3.3.4")
    //Validation
    implementation("org.springframework.boot:spring-boot-starter-validation:2.7.3")
    //AWS
    implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")
    //Security
    implementation("org.springframework.boot:spring-boot-starter-security")
    //Jwts
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    //data jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    //Jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    //mailing
    implementation("org.springframework.boot:spring-boot-starter-mail")
    //thymeleaf
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    //redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    //spring-doc
    implementation("org.springdoc:springdoc-openapi-ui:1.6.11")
    //mongo
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    //queryDSL
    implementation("com.querydsl:querydsl-jpa:5.0.0")
    kapt("com.querydsl:querydsl-apt:5.0.0:jpa")
    kapt("org.springframework.boot:spring-boot-configuration-processor")


}

sourceSets["main"].withConvention(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class) {
    kotlin.srcDir("$buildDir/generated/source/kapt/main")
}

//idea {
//    module {
//        val kaptMain = file("build/generated/source/kapt/main")
//        sourceDirs.add(kaptMain)
//        generatedSourceDirs.add(kaptMain)
//    }
//}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.getByName<Jar>("jar") {
    enabled = false
}

tasks.withType<Test> {
    useJUnitPlatform()
}
