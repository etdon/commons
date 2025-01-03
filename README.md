<p align="center">  
    <img src="https://i.imgur.com/Pyluzxt.png" width=830 height=207>    
</p>

## 🔰 Introduction

The `commons` library provides re-usable components and utilities for your Java project. The implementation is lightweight yet still feature-rich and requires no external run-time dependencies. While the project is targeting Java 8 to maintain compatability
with most projects it still focuses on modern and fluent implementations that allow you to streamline your code style.

Make sure to check out the project's [wiki page][wiki_page] for details regarding the available features as well as code examples.

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
The build management tool used for this project is [Apache Maven][build_tool]. Executing the following command will install the compiled artifact into your local repository if no critical issues occur during any of the lifecycle phases.
```
mvn clean install
```

## 🫴 Contributing
The contribution guidelines are a part of the `shared-guidelines` repository and can be found here: [Contributing][contributing]

## 📄 License
The `commons` project is licensed under the [Apache 2.0 License][license].
```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

[wiki_page]: https://docs.etdon.com/commons/
[build_tool]: https://maven.apache.org/
[contributing]: https://github.com/etdon/shared-guidelines/blob/main/CONTRIBUTING.md
[license]: https://github.com/etdon/commons/blob/master/LICENSE
