## TypeUri

[![Available on JitPack][jitpack_badge]][jitpack_link]

Lightweight Java library for wrapping and distinguishing URI type with ease.

## Taking a glance?

Check [test codes][test] to see what TypeUri can do.

## Download

Download via Maven:

```xml
<repositories>
    <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
    </repository>
  </repositories>

<dependency>
  <groupId>com.shaunkawano</groupId>
  <artifactId>typeuri</artifactId>
  <version>0.1.1</version>
</dependency>
```

or Gradle:

```gradle
allprojects {
  repositories {
    maven { url "https://jitpack.io" }
  }
}

dependencies {
  compile 'com.shaunkawano:typeuri:0.1.1'
}
```

## License

    Copyright 2016 Shohei Kawano

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[test]: https://github.com/shaunkawano/TypeUri/blob/master/typeuri/src/test/java/com/shaunkawano/TypeUriTest.java

[jitpack_badge]: https://jitpack.io/v/com.shaunkawano/typeuri.svg
[jitpack_link]: https://jitpack.io/#com.shaunkawano/typeuri
