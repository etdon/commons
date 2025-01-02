<p align="center">  
    <img src="https://i.imgur.com/Pyluzxt.png" width=1012 height=253>    
</p>

## 🔰 Introduction

The commons library provides re-usable components and utilities for your Java project. The implementation is lightweight yet still feature-rich and requires no external run-time dependencies. While the project is targeting Java 8 to maintain compatability
with most projects it still focuses on modern and fluent implementations that allow you to streamline your code style.

Make sure to check out the project's [wiki page](https://docs.etdon.com/commons/) for details regarding the available features as well as code examples.

## 🚀 Getting Started

> [!IMPORTANT]
> Requirements:
> - Java 8 (LTS)

🪶 Maven:
```xml
<repository>
    <id>etdon-repo</id>
    <url>https://repo.etdon.com/repository/maven-releases/</url>
</repository>
```

```xml
<dependency>
    <groupId>com.etdon</groupId>
    <artifactId>commons</artifactId>
    <version>1.0.0</version>
</dependency>
```

🐘 Gradle:
```groovy
maven {         
    url = uri("https://repo.etdon.com/repository/maven-releases/")
}
```

```groovy
dependencies {
    implementation 'com.etdon:commons:1.0.0'
}
```

<details>
  <summary>Latest snapshot</summary>

🪶 Maven:
```xml
<repository>
    <id>etdon-repo</id>
    <url>https://repo.etdon.com/repository/maven-snapshots/</url>
</repository>
```

```xml
<dependency>
    <groupId>com.etdon</groupId>
    <artifactId>commons</artifactId>
    <version>1.0.1-SNAPSHOT</version>
</dependency>
```

🐘 Gradle:
```groovy
maven {         
    url = uri("https://repo.etdon.com/repository/maven-snapshots/")
}
```

```groovy
dependencies {
    implementation 'com.etdon:commons:1.0.1-SNAPSHOT'
}
```

</details>

## 📦 Building
The build management tool used for this project is [Apache Maven](https://maven.apache.org/). Executing the following command will install the compiled artifact into your local repository if no critical issues occur during any of the lifecycle phases.
```
mvn clean install
```
