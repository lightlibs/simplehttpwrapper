# Simple HTTP Wrapper

This code provides a simple HTTP wrapper for making GET and POST requests. It includes classes and methods to perform HTTP requests and handle the response.

## SimpleHttpResponse

The `SimpleHttpResponse` class represents the response from an HTTP request. It contains the following methods:

- `getStatusCode()`: Returns the status code of the response.
- `getData()`: Returns the data (response body) of the response.

## SimpleHttpWrapper

The `SimpleHttpWrapper` class provides static methods to make HTTP requests. It includes the following methods:

- `get(String urlStr, String[] headers)`: Performs a GET request to the specified URL with optional headers and returns a `SimpleHttpResponse`.
- `post(String urlStr, String[] headers, String postData)`: Performs a POST request to the specified URL with optional headers and post data, and returns a `SimpleHttpResponse`.
- `performRequest(SupportedHttpMethod method, String urlStr, String[] headers, String postData)`: Performs an HTTP request with the specified method (GET or POST), URL, headers, and post data. Returns a `SimpleHttpResponse`.
- `performRequestUnsupported(String method, String urlStr, String[] headers, String postData)`: Discouraged method for performing an HTTP request with an unsupported method. Returns a `SimpleHttpResponse`.

## SupportedHttpMethod

The `SupportedHttpMethod` enum defines the supported HTTP methods. It includes the following methods:

- `getRawMethodName()`: Returns the raw method name as a `String`.

## Usage

To use the `SimpleHttpWrapper` class, import the `com.github.lightlibs.simplehttpwrapper` package and call the desired static methods. For example:

```java
import com.github.lightlibs.simplehttpwrapper.HttpResponse;
import com.github.lightlibs.simplehttpwrapper.HttpWrapper;
import com.github.lightlibs.simplehttpwrapper.SimpleHttpWrapper;

class Example {

    public static void main(String[] args) {
        // Perform a GET request
        HttpResponse response = HttpWrapper.get("https://example.com", null);
        int statusCode = response.getStatusCode();
        String data = response.getBody();

        // Perform a POST request with headers and post data
        String[] headers = {"Content-Type: application/json"};
        String postData = "{ \"name\": \"John\", \"age\": 30 }";
        HttpResponse response = HttpWrapper.post("https://example.com", headers, postData);
        int statusCode = response.getStatusCode();
        String data = response.getBody();
    }

}
```

## As a dependency
### Gradle
```groovy
    repositories {
        maven { url 'https://jitpack.io' }
    }
```

```groovy
    dependencies {
        implementation 'com.github.lightlibs:simplehttpwrapper:[VERSION-STRING]'
    }
```

### Maven
```xml
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
         </repository>
    </repositories>
```

```xml
    <dependency>
        <groupId>com.github.lightlibs</groupId>
        <artifactId>simplehttpwrapper</artifactId>
        <version>[VERSION-STRING]</version>
    </dependency>
```

Note: Make sure to handle `IOException` when making the requests.

Feel free to customize the code or add additional functionality as needed. And make a PR ;)