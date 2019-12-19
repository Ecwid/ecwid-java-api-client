

Simple example:
```
val apiClient = ApiClient.create(
		apiServerDomain = ApiServerDomain(),
		storeCredentials = ApiStoreCredentials(
				storeId = 1003,
				apiToken = "secret_mysecuretoken"),
		httpTransport = ApacheCommonsHttpClientTransport(),
		jsonTransformerProvider = object : JsonTransformerProvider {
			override fun build(polymorphicTypes: Set<PolymorphicType<*>>) = GsonJsonTransformer(polymorphicTypes)
		})

val customer = apiClient.getCustomerDetails(CustomerDetailsRequest(customerId = 1))
println("api/v3 customer: $customer")
```

Batch example:
```
val apiClient = ApiClient.create(
		apiServerDomain = ApiServerDomain(),
		storeCredentials = ApiStoreCredentials(
				storeId = 1003,
				apiToken = "secret_mysecuretoken"),
		httpTransport = ApacheCommonsHttpClientTransport(),
		jsonTransformerProvider = object : JsonTransformerProvider {
			override fun build(polymorphicTypes: Set<PolymorphicType<*>>) = GsonJsonTransformer(polymorphicTypes)
		})

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
    break;
}
```