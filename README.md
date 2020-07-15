# tvtime-client

![Tests](https://github.com/srgsf/tvtime-client/workflows/Tests/badge.svg?branch=master&event=push)

A Java wrapper around https://tvtime.com [Rest API](https://api.tvtime.com/doc) using [Retrofit 2](https://square.github.io/retrofit/) and [Moshi](https://github.com/square/moshi#readme) for json serialization.

Pull requests are welcome.

## Usage

Add the following dependency to your Gradle project:

```groovy
implementation 'com.github.srgsf:tvtime-client:1.0'
```

Or for Maven:

```xml
<dependency>
  <groupId>com.github.srgsf</groupId>
  <artifactId>tvtime-client</artifactId>
  <version>1.0</version>
</dependency>
```


### Authorization
The TV Time API can only be accessed via OAuth 2.0.

Current version supports Web Application flow and Limited Device flow. See `TvTimeAuthClient` for details.

Example of Limited Device flow token polling.
```java
     TvTimeAuthClient authClient = new TvTimeAuthClient.Builder()
                    .clientCredentials("APIClientId", "APIClientSecret")
                    .build();
    
            DeviceCode code = null;
            Response<DeviceCode> codeResponse = authClient.deviceCode();
            if (codeResponse.isSuccessful()) {
                code = codeResponse.body();
                String token = token(code);
            }
            //show code.userCode and code.verificationUrl to the user.
...

String token(DeviceCode code) throws IOException, InterruptedException {
        Instant expire = Instant.now().plusSeconds(code.expiresIn);
        //polling
        while (true) {
            Thread.sleep(code.interval * 1000);
            Response<String> tokenResponse = authClient.accessToken(code.deviceCode);
            if (tokenResponse.isSuccessful()) {
                return tokenResponse.body();
            }

            if (Instant.now().isAfter(expire)) {
                return null;
            }

        }
    }

```
### Example

Use like any other retrofit2 based service.  
Optionally you can share OkHttp client and Retrofit instances to keep single request pooling, disk cache, routing logic, etc.

```java
TvTime client = new TvTime.Builder()
                .tokenProvider(() -> "<TOKEN>")
                .build();
TvTimeInfo api = client.info();
Response<User> response = api.user().execute();
    if (response.isSuccessful()) {
        User u = response.body();
        //use user
    }
```

See test cases in `src/test/` for more examples.

### Known API Issues

* [Retrieve Progress](https://api.tvtime.com/doc#retrieve_progress) method always responses with `null` progress.
* [Checkin](https://api.tvtime.com/doc#checkin), [Save Progress](https://api.tvtime.com/doc#save_progress) and [Retrieve Progress](https://api.tvtime.com/doc#retrieve_progress) methods don't work if `show_id`,`season_number` and `number` are sent as [Episode Item](https://api.tvtime.com/doc#episode_item_parameter) parameter.