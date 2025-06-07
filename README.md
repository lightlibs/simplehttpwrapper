# Simple HTTP Wrapper (SimpleHTTPWrapper)
Author: LightLibs  
License: MIT

This library provides a simple HTTP wrapper for making GET, POST, and other requests. It includes classes and methods to 
perform HTTP requests and handle the response.

## HttpResponse

The `HttpResponse` class represents the response from an HTTP request. It contains the following methods:

- `getStatusCode()`: Returns the status code of the response.
- `getHeaders()`: Returns the headers of the response.
- `getBody()`: Returns the body (response data) of the response.

## HttpWrapper

The `HttpWrapper` class provides static methods to make HTTP requests. It includes the following methods (and more):

- `get(String urlStr, String[] headers)`: Performs a GET request to the specified URL with optional headers and returns 
  a `SimpleHttpResponse`.
- `post(String urlStr, String[] headers, String postData)`: Performs a POST request to the specified URL with 
  optionalheaders and post data, and returns a `SimpleHttpResponse`.
- `performRequest(SupportedHttpMethod method, String urlStr, String[] headers, String postData)`: Performs an HTTP 
  request with the specified method (GET or POST), URL, headers, and post data. Returns a `SimpleHttpResponse`.
- `performRequestUnsupported(String method, String urlStr, String[] headers, String postData)`: Discouraged method for 
  performing an HTTP request with an unsupported method. Returns a `SimpleHttpResponse`.

## HttpMethod

The `HttpMethod` enum defines the supported HTTP methods. It includes the following methods:

- `getMethodId()`: Returns the raw method name as a `String`.

## Usage

To use the `HttpWrapper` class, import the `com.github.lightlibs.simplehttpwrapper` package and call the desired static 
methods. For example:

```java
import com.tcoded.lightlibs.simplehttpwrapper.HttpHeader;
import com.tcoded.lightlibs.simplehttpwrapper.HttpResponse;
import com.tcoded.lightlibs.simplehttpwrapper.HttpWrapper;

class Example {

  public static void main(String[] args) {
    // Perform a GET request
    HttpResponse response = HttpWrapper.get("https://example.com", null);
    int statusCode = response.getStatusCode();
    String data = response.getBody();

    // Perform a POST request with headers and post data
    String[] headers = {"Content-Type: application/json"};
    String postData = "{ \"name\": \"John\", \"age\": 30 }";

    // Make the request
    HttpResponse response = HttpWrapper.post("https://example.com", headers, postData);
    // Alternative request which doesn't throw an IOException
    HttpResponse responseOrNull = HttpWrapper.postOrNull("https://example.com", headers, postData);

    // Read the response from the server
    int statusCode = response.getStatusCode();
    List<HttpHeader> responseHeaders = response.getHeaders();
    String responseBody = response.getBody();
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
        implementation 'com.github.lightlibs:SimpleHttpWrapper:0.0.5'
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
        <artifactId>SimpleHttpWrapper</artifactId>
        <version>0.0.5</version>
    </dependency>
```

Note: Make sure to catch any `IOException` when making the requests.

Feel free to customize the code or add additional functionality as needed. And make a PR ;)