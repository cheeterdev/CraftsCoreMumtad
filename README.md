# CraftsCore
![CraftsCore](https://img.shields.io/badge/Based%20on-CraftsCore%20v3.7.59-SNAPSHOT)
![License](https://img.shields.io/badge/License-AGPL%203.0-blue)
![Mumtad](https://img.shields.io/badge/Mumtad-Info%20below.-red)

## Installation Info
We didnt edited the Craftscore yet, the Installation below is for [the unedited original CraftsCore.](https://github.com/CrAfTsArMy/CraftsCore) When we release the first version of our edited CraftsCore we will [announce it in our Discord Server.](https://dsc.gg/cheeter)

### Maven
```xml
<repositories>
  ...
  <repository>
    <id>craftsblock-releases</id>
    <name>CraftsBlock Repositories</name>
    <url>https://repo.craftsblock.de/releases</url>
  </repository>
</repositories>
```
```xml
<dependencies>
  ...
  <dependency>
    <groupId>de.craftsblock.craftscore</groupId>
    <artifactId>CraftsCore</artifactId>
    <version>X.X.X-SNAPSHOT</version>
  </dependency>
</dependencies>
```

### Gradle
```gradle
repositories {
  ...
  maven { url "https://repo.craftsblock.de/releases" }
  mavenCentral()
}
```
```gradle
dependencies {
  ...
  implementation 'de.craftsblock.craftscore:CraftsCore:X.X.X-SNAPSHOT'
}
```

## Open Source Licenses
We are using some third party open source libraries. Below you find a list of all third party open source libraries used:
| Name                                                                   | Description                                                                                                                           | Licecnse                                                                                         |
| ---------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------ |
| [OkHttp](https://github.com/square/okhttp)                             | Square’s meticulous HTTP client for the JVM, Android, and GraalVM.                                                                    | [Apache License 2.0](https://github.com/square/okhttp/blob/master/LICENSE.txt)                   |
| [MySQL Connector-J](https://github.com/mysql/mysql-connector-j)        | MySQL Connector/J                                                                                                                     | [GPLv2 with FOSS exception](https://github.com/mysql/mysql-connector-j/blob/release/8.x/LICENSE) |
| [GSON](https://github.com/google/gson)                                 | A Java serialization/deserialization library to convert Java Objects into JSON and back                                               | [Apache License 2.0](https://github.com/google/gson/blob/main/LICENSE)                           |

## Issues
If you find any issues from the plugin or documentation please [open up issue](https://github.com/CrAfTsArMy/CraftsCore/issues)
