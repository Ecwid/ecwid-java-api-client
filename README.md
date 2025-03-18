# Ecwid java api client library ![](https://github.com/Ecwid/ecwid-java-api-client/workflows/Gradle%20Package/badge.svg)

API documentation: https://api-docs.ecwid.com/reference/overview 

#### Configure client library:
You can test this api client library on you local storage. 
For this you need to copy test/resources/test.properties.sample renamed to test.properties
and configure it

For example: `test.properties`
```properties
storeId=2
apiToken=secret_4T6z...
apiHost=app.local.ecwid.com
apiPort=8443
```

#### Adding the Library to a Maven Project

To add the current library to a Maven project, you need to add the following block of code to your project's `pom.xml` file inside the `<dependencies>` tag:

```xml
<dependency>
    <groupId>com.ecwid.apiclient</groupId>
    <artifactId>api-client</artifactId>
    <version>0.326.0</version>
</dependency>
```

#### Adding the Library to a Gradle Project

To add the current library to a Gradle project using Kotlin DSL, you need to add the following line to your project's `build.gradle.kts` file inside the `dependencies` block:

```kotlin
implementation("com.ecwid.apiclient:api-client:0.326.0")
```

## Examples

#### Simple example:
```kotlin
val apiClient = ApiClient.create(
		apiServerDomain = ApiServerDomain(),
		storeCredentials = ApiStoreCredentials(
				storeId = 1003,
				apiToken = "secret_mysecuretoken"),
		httpTransport = ApacheCommonsHttpClientTransport(),
		jsonTransformerProvider = GsonTransformerProvider())

val customer = apiClient.getCustomerDetails(CustomerDetailsRequest(customerId = 1))
println("api/v3 customer: $customer")
```

#### Batch api example:
```kotlin
val apiClient = ApiClient.create(
		apiServerDomain = ApiServerDomain(),
		storeCredentials = ApiStoreCredentials(
				storeId = 1003,
				apiToken = "secret_mysecuretoken"),
		httpTransport = ApacheCommonsHttpClientTransport(),
		jsonTransformerProvider = GsonTransformerProvider())

val requestsForBatch = listOf(CustomerDetailsRequest(1), CustomerDetailsRequest(2))
val batch = apiClient.createBatch(CreateBatchRequest(requestsForBatch, stopOnFirstFailure = true))

while (true) {
	val typedBatch = apiClient.getTypedBatch(GetEscapedBatchRequest(batch.ticket))
	if (typedBatch.status != BatchStatus.COMPLETED) {
		TimeUnit.SECONDS.sleep(2)
		continue
	}
	val customers = typedBatch.responses.orEmpty()
			.map { it.toTypedResponse(FetchedCustomer::class.java) }
			.mapNotNull { if (it is TypedBatchResponse.Ok<FetchedCustomer>) it.value else null }
	val errors = typedBatch.responses.orEmpty()
			.map { it.toTypedResponse(FetchedCustomer::class.java) }
			.mapNotNull { if (it !is TypedBatchResponse.Ok<FetchedCustomer>) it.toString() else null }
	println("api/v3 customers: ${customers.joinToString { it.id.toString() }}, errors: ${errors.joinToString()}")
	break
}
```

Testing
