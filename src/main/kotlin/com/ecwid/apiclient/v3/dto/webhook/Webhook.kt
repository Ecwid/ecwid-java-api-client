package com.ecwid.apiclient.v3.dto.webhook

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO
import com.google.gson.annotations.SerializedName

@Suppress("unused")
data class Webhook(
	val eventId: String = "",
	val eventCreated: Long = 0L,
	val storeId: Long = 0L,
	val entityId: Long = 0L,
	val eventType: EventType = EventType.APPLICATION_INSTALLED,
	val data: Map<String, String> = emptyMap(),
) : ApiResultDTO {

	enum class EventType {
		@SerializedName("application.installed")
		APPLICATION_INSTALLED,

		@SerializedName("application.uninstalled")
		APPLICATION_UNINSTALLED,

		@SerializedName("application.subscription_status_changed")
		APPLICATION_SUBSCRIPTION_STATUS_CHANGED,

		@SerializedName("application.storage_changed")
		APPLICATION_STORAGE_CHANGED,

		@SerializedName("category.created")
		CATEGORY_CREATED,

		@SerializedName("category.deleted")
		CATEGORY_DELETED,

		@SerializedName("category.updated")
		CATEGORY_UPDATED,

		@SerializedName("customer.created")
		CUSTOMER_CREATED,

		@SerializedName("customer.deleted")
		CUSTOMER_DELETED,

		@SerializedName("customer.updated")
		CUSTOMER_UPDATED,

		@SerializedName("customer.personal_data_removal_request")
		CUSTOMER_PERSONAL_DATA_REMOVAL_REQUEST,

		@SerializedName("customer.personal_data_export_request")
		CUSTOMER_PERSONAL_DATA_EXPORT_REQUEST,

		@SerializedName("order.created")
		ORDER_CREATED,

		@SerializedName("order.deleted")
		ORDER_DELETED,

		@SerializedName("order.updated")
		ORDER_UPDATED,

		@SerializedName("product.created")
		PRODUCT_CREATED,

		@SerializedName("product.deleted")
		PRODUCT_DELETED,

		@SerializedName("product.updated")
		PRODUCT_UPDATED,

		@SerializedName("product_class.created")
		PRODUCT_CLASS_CREATED,

		@SerializedName("product_class.deleted")
		PRODUCT_CLASS_DELETED,

		@SerializedName("product_class.updated")
		PRODUCT_CLASS_UPDATED,

		@SerializedName("review.created")
		REVIEW_CREATED,

		@SerializedName("review.deleted")
		REVIEW_DELETED,

		@SerializedName("review.updated")
		REVIEW_UPDATED,

		@SerializedName("profile.updated")
		PROFILE_UPDATED,

		@SerializedName("profile.subscription_status_changed")
		PROFILE_SUBSCRIPTION_STATUS_CHANGED,

		@SerializedName("profile.personal_data_removal_request")
		PROFILE_PERSONAL_DATA_REMOVAL_REQUEST,

		@SerializedName("profile.personal_data_export_request")
		PROFILE_PERSONAL_DATA_EXPORT_REQUEST,

		@SerializedName("unfinished_order.created")
		UNFINISHED_ORDER_CREATED,

		@SerializedName("unfinished_order.deleted")
		UNFINISHED_ORDER_DELETED,

		@SerializedName("unfinished_order.updated")
		UNFINISHED_ORDER_UPDATED,

		@SerializedName("discount_coupon.created")
		DISCOUNT_COUPON_CREATED,

		@SerializedName("discount_coupon.deleted")
		DISCOUNT_COUPON_DELETED,

		@SerializedName("discount_coupon.updated")
		DISCOUNT_COUPON_UPDATED,

		@SerializedName("invoice.created")
		INVOICE_CREATED,

		@SerializedName("invoice.deleted")
		INVOICE_DELETED,

		@SerializedName("promotion.created")
		PROMOTION_CREATED,

		@SerializedName("promotion.deleted")
		PROMOTION_DELETED,

		@SerializedName("promotion.updated")
		PROMOTION_UPDATED,

		@SerializedName("customer_group.created")
		CUSTOMER_GROUP_CREATED,

		@SerializedName("customer_group.updated")
		CUSTOMER_GROUP_UPDATED,

		@SerializedName("customer_group.deleted")
		CUSTOMER_GROUP_DELETED,
	}

}
