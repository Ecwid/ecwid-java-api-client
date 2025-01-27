package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.config.ApiServerDomain
import com.ecwid.apiclient.v3.config.ApiStoreCredentials
import com.ecwid.apiclient.v3.config.LoggingSettings
import com.ecwid.apiclient.v3.dto.application.request.ApplicationDeleteRequest
import com.ecwid.apiclient.v3.dto.application.request.ApplicationTokenRequest
import com.ecwid.apiclient.v3.dto.application.result.ApplicationDeleteResult
import com.ecwid.apiclient.v3.dto.application.result.ApplicationTokenResult
import com.ecwid.apiclient.v3.dto.batch.request.CancelBatchGroupRequest
import com.ecwid.apiclient.v3.dto.batch.request.CreateBatchRequest
import com.ecwid.apiclient.v3.dto.batch.request.CreateBatchRequestWithIds
import com.ecwid.apiclient.v3.dto.batch.request.GetEscapedBatchRequest
import com.ecwid.apiclient.v3.dto.batch.result.CancelBatchGroupResult
import com.ecwid.apiclient.v3.dto.batch.result.CreateBatchResult
import com.ecwid.apiclient.v3.dto.batch.result.GetEscapedBatchResult
import com.ecwid.apiclient.v3.dto.batch.result.GetTypedBatchResult
import com.ecwid.apiclient.v3.dto.cart.request.*
import com.ecwid.apiclient.v3.dto.cart.result.*
import com.ecwid.apiclient.v3.dto.common.PartialResult
import com.ecwid.apiclient.v3.dto.coupon.request.*
import com.ecwid.apiclient.v3.dto.coupon.result.*
import com.ecwid.apiclient.v3.dto.customer.request.*
import com.ecwid.apiclient.v3.dto.customer.result.*
import com.ecwid.apiclient.v3.dto.customergroup.request.*
import com.ecwid.apiclient.v3.dto.customergroup.result.*
import com.ecwid.apiclient.v3.dto.instantsite.redirects.request.*
import com.ecwid.apiclient.v3.dto.instantsite.redirects.result.*
import com.ecwid.apiclient.v3.dto.productreview.request.*
import com.ecwid.apiclient.v3.dto.productreview.result.*
import com.ecwid.apiclient.v3.dto.producttype.request.*
import com.ecwid.apiclient.v3.dto.producttype.result.*
import com.ecwid.apiclient.v3.dto.report.request.ReportAdviceRequest
import com.ecwid.apiclient.v3.dto.report.request.ReportRequest
import com.ecwid.apiclient.v3.dto.report.result.FetchedReportAdviceResponse
import com.ecwid.apiclient.v3.dto.report.result.FetchedReportResponse
import com.ecwid.apiclient.v3.dto.saleschannels.request.*
import com.ecwid.apiclient.v3.dto.saleschannels.response.*
import com.ecwid.apiclient.v3.dto.sluginfo.FetchedSlugInfo
import com.ecwid.apiclient.v3.dto.sluginfo.SlugInfoRequest
import com.ecwid.apiclient.v3.dto.storage.request.*
import com.ecwid.apiclient.v3.dto.storage.result.*
import com.ecwid.apiclient.v3.dto.subscriptions.request.SubscriptionsSearchRequest
import com.ecwid.apiclient.v3.dto.subscriptions.result.FetchedSubscription
import com.ecwid.apiclient.v3.dto.subscriptions.result.SubscriptionsSearchResult
import com.ecwid.apiclient.v3.dto.swatches.request.RecentSwatchColorsGetRequest
import com.ecwid.apiclient.v3.dto.swatches.result.FetchedSwatchColorsResult
import com.ecwid.apiclient.v3.dto.variation.request.*
import com.ecwid.apiclient.v3.dto.variation.result.*
import com.ecwid.apiclient.v3.httptransport.HttpTransport
import com.ecwid.apiclient.v3.impl.*
import com.ecwid.apiclient.v3.jsontransformer.JsonTransformerProvider
import kotlin.reflect.KClass

open class ApiClient private constructor(
	protected val apiClientHelper: ApiClientHelper,
	storeProfileApiClient: StoreProfileApiClient,
	brandsApiClient: BrandsApiClient,
	productsApiClient: ProductsApiClient,
	categoriesApiClient: CategoriesApiClient,
	ordersApiClient: OrdersApiClient,
	productTypesApiClient: ProductTypesApiClient,
	customersApiClient: CustomersApiClient,
	customerGroupsApiClient: CustomerGroupsApiClient,
	productVariationsApiClient: ProductVariationsApiClient,
	batchApiClient: BatchApiClient,
	discountCouponsApiClient: CouponsApiClient,
	cartsApiClient: CartsApiClient,
	salesChannelsApiClient: SalesChannelsApiClient,
	applicationApiClient: ApplicationApiClient,
	applicationStorageApiClient: ApplicationStorageApiClient,
	reportsApiClient: ReportsApiClientImpl,
	subscriptionsApiClient: SubscriptionsApiClientImpl,
	instantSiteRedirectsApiClient: InstantSiteRedirectsApiClientImpl,
	slugInfoApiClient: SlugInfoApiClientImpl,
	productReviewsApiClient: ProductReviewsApiClientImpl,
	storeExtrafieldsApiClient: StoreExtrafieldsApiClientImpl,
	swatchesApiClient: SwatchesApiClientImpl,
) :
	StoreProfileApiClient by storeProfileApiClient,
	BrandsApiClient by brandsApiClient,
	ProductsApiClient by productsApiClient,
	CategoriesApiClient by categoriesApiClient,
	OrdersApiClient by ordersApiClient,
	ProductTypesApiClient by productTypesApiClient,
	CustomersApiClient by customersApiClient,
	CustomerGroupsApiClient by customerGroupsApiClient,
	ProductVariationsApiClient by productVariationsApiClient,
	BatchApiClient by batchApiClient,
	CouponsApiClient by discountCouponsApiClient,
	CartsApiClient by cartsApiClient,
	SalesChannelsApiClient by salesChannelsApiClient,
	ApplicationApiClient by applicationApiClient,
	ApplicationStorageApiClient by applicationStorageApiClient,
	ReportsApiClient by reportsApiClient,
	SubscriptionsApiClient by subscriptionsApiClient,
	InstantSiteRedirectsApiClient by instantSiteRedirectsApiClient,
	SlugInfoApiClient by slugInfoApiClient,
	ProductReviewsApiClient by productReviewsApiClient,
	StoreExtrafieldsApiClient by storeExtrafieldsApiClient,
	SwatchesApiClient by swatchesApiClient {

	constructor(apiClientHelper: ApiClientHelper) : this(
		apiClientHelper = apiClientHelper,
		storeProfileApiClient = StoreProfileApiClientImpl(apiClientHelper),
		brandsApiClient = BrandsApiClientImpl(apiClientHelper),
		productsApiClient = ProductsApiClientImpl(apiClientHelper),
		categoriesApiClient = CategoriesApiClientImpl(apiClientHelper),
		ordersApiClient = OrdersApiClientImpl(apiClientHelper),
		productTypesApiClient = ProductTypesApiClientImpl(apiClientHelper),
		customersApiClient = CustomersApiClientImpl(apiClientHelper),
		customerGroupsApiClient = CustomerGroupsApiClientImpl(apiClientHelper),
		productVariationsApiClient = ProductVariationsApiClientImpl(apiClientHelper),
		batchApiClient = BatchApiClientImpl(apiClientHelper),
		discountCouponsApiClient = CouponsApiClientImpl(apiClientHelper),
		cartsApiClient = CartsApiClientImpl(apiClientHelper),
		salesChannelsApiClient = SalesChannelsApiClientImpl(apiClientHelper),
		applicationApiClient = ApplicationApiClientImpl(apiClientHelper),
		applicationStorageApiClient = ApplicationStorageApiClientImpl(apiClientHelper),
		reportsApiClient = ReportsApiClientImpl(apiClientHelper),
		subscriptionsApiClient = SubscriptionsApiClientImpl(apiClientHelper),
		instantSiteRedirectsApiClient = InstantSiteRedirectsApiClientImpl(apiClientHelper),
		slugInfoApiClient = SlugInfoApiClientImpl(apiClientHelper),
		productReviewsApiClient = ProductReviewsApiClientImpl(apiClientHelper),
		storeExtrafieldsApiClient = StoreExtrafieldsApiClientImpl(apiClientHelper),
		swatchesApiClient = SwatchesApiClientImpl(apiClientHelper),
	)

	companion object {

		fun create(
			apiServerDomain: ApiServerDomain,
			storeCredentials: ApiStoreCredentials,
			loggingSettings: LoggingSettings = LoggingSettings(),
			httpTransport: HttpTransport,
			jsonTransformerProvider: JsonTransformerProvider
		): ApiClient {
			val apiClientHelper = ApiClientHelper(
				apiServerDomain = apiServerDomain,
				storeCredentials = storeCredentials,
				loggingSettings = loggingSettings,
				httpTransport = httpTransport,
				jsonTransformerProvider = jsonTransformerProvider
			)
			return ApiClient(apiClientHelper)
		}
	}
}

// Product types
// https://developers.ecwid.com/api-documentation/product-types
interface ProductTypesApiClient {
	fun getAllProductTypes(request: ProductTypesGetAllRequest): ProductTypesGetAllResult
	fun getProductTypeDetails(request: ProductTypeDetailsRequest): FetchedProductType
	fun createProductType(request: ProductTypeCreateRequest): ProductTypeCreateResult
	fun updateProductType(request: ProductTypeUpdateRequest): ProductTypeUpdateResult
	fun deleteProductType(request: ProductTypeDeleteRequest): ProductTypeDeleteResult
}

// Customers
// https://developers.ecwid.com/api-documentation/customers
interface CustomersApiClient {
	fun searchCustomers(request: CustomersSearchRequest): CustomersSearchResult
	fun searchCustomersAsSequence(request: CustomersSearchRequest): Sequence<FetchedCustomer>
	fun getCustomerDetails(request: CustomerDetailsRequest): FetchedCustomer
	fun createCustomer(request: CustomerCreateRequest): CustomerCreateResult
	fun updateCustomer(request: CustomerUpdateRequest): CustomerUpdateResult
	fun deleteCustomer(request: CustomerDeleteRequest): CustomerDeleteResult
	fun searchDeletedCustomers(request: DeletedCustomersSearchRequest): DeletedCustomersSearchResult
	fun searchDeletedCustomersAsSequence(request: DeletedCustomersSearchRequest): Sequence<DeletedCustomer>
	fun searchCustomersProducts(request: CustomersProductsSearchRequest): CustomersProductsSearchResult
	fun searchCustomersLocations(request: CustomersLocationsSearchRequest): CustomersLocationsSearchResult
	fun searchCustomersFilters(request: CustomerFiltersDataSearchRequest): CustomersFiltersDataSearchResult
	fun massUpdate(request: CustomersMassUpdateRequest): CustomerUpdateResult
	fun getCustomersIds(request: CustomersIdsRequest): CustomersIdsResult
}

// Customer groups
// https://developers.ecwid.com/api-documentation/customer-groups
interface CustomerGroupsApiClient {
	fun searchCustomerGroups(request: CustomerGroupsSearchRequest): CustomerGroupsSearchResult
	fun searchCustomerGroupsAsSequence(request: CustomerGroupsSearchRequest): Sequence<FetchedCustomerGroup>
	fun getCustomerGroupDetails(request: CustomerGroupDetailsRequest): FetchedCustomerGroup
	fun createCustomerGroup(request: CustomerGroupCreateRequest): CustomerGroupCreateResult
	fun updateCustomerGroup(request: CustomerGroupUpdateRequest): CustomerGroupUpdateResult
	fun deleteCustomerGroup(request: CustomerGroupDeleteRequest): CustomerGroupDeleteResult
}

// Batch requests
// https://developers.ecwid.com/api-documentation/batch-requests
interface BatchApiClient {
	fun createBatch(request: CreateBatchRequestWithIds): CreateBatchResult
	fun createBatch(request: CreateBatchRequest): CreateBatchResult
	fun cancelBatchGroup(request: CancelBatchGroupRequest): CancelBatchGroupResult
	fun getTypedBatch(request: GetEscapedBatchRequest): GetTypedBatchResult
	fun getEscapedBatch(request: GetEscapedBatchRequest): GetEscapedBatchResult
}

// Store information
// https://developers.ecwid.com/api-documentation/store-information
// TODO

// Product variations
// https://developers.ecwid.com/api-documentation/product-variations
// TODO
interface ProductVariationsApiClient {
	fun createProductVariation(request: CreateProductVariationRequest): CreateProductVariationResult
	fun uploadVariationImage(request: ProductVariationImageUploadRequest): ProductVariationImageUploadResult
	fun uploadProductVariationImageAsync(request: ProductVariationImageAsyncUploadRequest): ProductVariationImageAsyncUploadResult
	fun getAllProductVariations(request: ProductVariationsRequest): ProductVariationsResult
	fun <Result> getAllProductVariations(request: ProductVariationsRequest, resultClass: KClass<Result>): List<Result>
		where Result : PartialResult<FetchedVariation>
	fun getProductVariation(request: ProductVariationDetailsRequest): FetchedVariation
	fun <Result> getProductVariation(request: ProductVariationDetailsRequest, resultClass: KClass<Result>): Result
		where Result : PartialResult<FetchedVariation>
	fun updateProductVariation(request: UpdateProductVariationRequest): UpdateProductVariationResult
	fun deleteProductVariation(request: DeleteProductVariationRequest): DeleteProductVariationsResult
	fun deleteAllProductVariations(request: DeleteAllProductVariationsRequest): DeleteProductVariationsResult
	fun adjustVariationInventory(request: AdjustVariationInventoryRequest): AdjustVariationInventoryResult
	fun deleteVariationImage(request: ProductVariationImageDeleteRequest): ProductVariationImageDeleteResult
}

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
inline fun <reified Result : PartialResult<FetchedVariation>> ProductVariationsApiClient.getProductVariation(request: ProductVariationDetailsRequest): Result {
	return getProductVariation(request, Result::class)
}

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
inline fun <reified Result : PartialResult<FetchedVariation>> ProductVariationsApiClient.getAllProductVariations(request: ProductVariationsRequest): List<Result> {
	return getAllProductVariations(request, Result::class)
}

// Carts
// https://developers.ecwid.com/api-documentation/carts
interface CartsApiClient {
	fun searchCarts(request: CartsSearchRequest): CartsSearchResult
	fun searchCartsAsSequence(request: CartsSearchRequest): List<FetchedCart>
	fun getCartDetails(request: CartDetailsRequest): FetchedCart
	fun updateCart(request: CartUpdateRequest): CartUpdateResult
	fun calculateOrderDetails(request: CalculateOrderDetailsRequest): CalculateOrderDetailsResult
	fun convertCartToOrder(request: ConvertCartToOrderRequest): ConvertCartToOrderResult
}

// Discount coupons
// https://developers.ecwid.com/api-documentation/discount-coupons
interface CouponsApiClient {
	fun searchCoupons(request: CouponSearchRequest): CouponSearchResult
	fun searchCouponsAsSequence(request: CouponSearchRequest): Sequence<FetchedCoupon>
	fun getCouponDetails(request: CouponDetailsRequest): FetchedCoupon
	fun createCoupon(request: CouponCreateRequest): CouponCreateResult
	fun updateCoupon(request: CouponUpdateRequest): CouponUpdateResult
	fun deleteCoupon(request: CouponDeleteRequest): CouponDeleteResult
}

interface SalesChannelsApiClient {
	fun getGoogleShoppingFeedConfig(request: GoogleShoppingFeedConfigGetRequest): FetchedGoogleShoppingFeedConfig
	fun getShopzillaFeedConfig(request: ShopzillaFeedConfigGetRequest): FetchedShopzillaFeedConfig
	fun getYahooShoppingFeedConfig(request: YahooShoppingFeedConfigGetRequest): FetchedYahooShoppingFeedConfig
	fun getYandexMarketFeedConfig(request: YandexMarketFeedConfigGetRequest): FetchedYandexMarketFeedConfig
	fun getYandexMarketDbsFeedConfig(request: YandexMarketDbsFeedConfigGetRequest): FetchedYandexMarketDbsFeedConfig
}

// Application
// https://developers.ecwid.com/api-documentation/application
interface ApplicationApiClient {
	fun deleteApplication(request: ApplicationDeleteRequest): ApplicationDeleteResult
	fun getApplicationToken(request: ApplicationTokenRequest): ApplicationTokenResult
}

// Application storage
// https://developers.ecwid.com/api-documentation/app-storage
interface ApplicationStorageApiClient {
	fun getStorageData(request: StorageDataRequest): FetchedStorageData
	fun getAllStorageData(request: AllStorageDataRequest): AllStorageDataResult
	fun createStorageData(request: StorageDataCreateRequest): StorageDataCreateResult
	fun updateStorageData(request: StorageDataUpdateRequest): StorageDataUpdateResult
	fun deleteStorageData(request: StorageDataDeleteRequest): StorageDataDeleteResult
}

// Starter site
// https://developers.ecwid.com/api-documentation/starter-site
// TODO

// Static store pages
// https://developers.ecwid.com/api-documentation/static-store-pages
// TODO

// Report API
interface ReportsApiClient {
	fun fetchReport(request: ReportRequest): FetchedReportResponse
	fun getReportAdvice(request: ReportAdviceRequest): FetchedReportAdviceResponse
}

// Recurring subscriptions
// https://api-docs.ecwid.com/reference/get-subscription
interface SubscriptionsApiClient {
	fun searchSubscriptions(request: SubscriptionsSearchRequest): SubscriptionsSearchResult
	fun searchSubscriptionsAsSequence(request: SubscriptionsSearchRequest): Sequence<FetchedSubscription>
}

interface InstantSiteRedirectsApiClient {
	fun searchInstantSiteRedirects(request: InstantSiteRedirectsSearchRequest): InstantSiteRedirectsSearchResult
	fun getInstantSiteRedirectsForExactPath(request: InstantSiteRedirectsGetForExactPathRequest): InstantSiteRedirectsGetForExactPathResult
	fun getInstantSiteRedirect(request: InstantSiteRedirectGetRequest): FetchedInstantSiteRedirect
	fun updateInstantSiteRedirect(request: InstantSiteRedirectUpdateRequest): InstantSiteRedirectsUpdateResult
	fun createInstantSiteRedirects(request: InstantSiteRedirectsCreateRequest): InstantSiteRedirectsCreateResult
	fun deleteInstantSiteRedirect(request: InstantSiteRedirectDeleteRequest): InstantSiteRedirectsDeleteResult
}

interface SlugInfoApiClient {
	fun getSlugInfo(request: SlugInfoRequest): FetchedSlugInfo
}

// Product reviews
interface ProductReviewsApiClient {
	fun searchProductReviews(request: ProductReviewSearchRequest): ProductReviewSearchResult
	fun searchProductReviewsAsSequence(request: ProductReviewSearchRequest): Sequence<FetchedProductReview>
	fun getProductReviewDetails(request: ProductReviewDetailsRequest): FetchedProductReview
	fun updateProductReviewStatus(request: ProductReviewUpdateStatusRequest): ProductReviewUpdateStatusResult
	fun deleteProductReview(request: ProductReviewDeleteRequest): ProductReviewDeleteResult
	fun massUpdateProductReview(request: ProductReviewMassUpdateRequest): ProductReviewMassUpdateResult
	fun getProductReviewsFiltersData(request: ProductReviewFiltersDataRequest): ProductReviewFiltersDataResult
}

// Swatches
interface SwatchesApiClient {
	fun getRecentSwatchColors(request: RecentSwatchColorsGetRequest): FetchedSwatchColorsResult
}
