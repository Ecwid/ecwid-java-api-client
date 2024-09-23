package com.ecwid.apiclient.v3.dto.report.enums

/**
 * Report types here are grouped by sections, they are shown on the CP:
 * visitors, orders, finance, marketing, conversions,
 * Add a new report type to the section, it belongs to.
 */
enum class ReportType {
	/** visitors */
	allTraffic,
	newVsReturningVisitors,
	visitorsByDevice,
	visitorsByCountry,
	visitorsByLanguage,
	topOfLandingPagesByVisitors,

	/** orders */
	allOrders,
	newOrdersVsRepeatOrders,
	topOfCustomersByOrders,
	inventory,
	topOfShippingMethodsByOrders,
	topOfPaymentMethodsByOrders,
	topOfCouponsByOrders,
	topOfCategoriesByOrders,

	/** finance */
	allRevenue,
	allExpenses,
	allProfit,

	/** marketing */
	customersByMarketingEmailsConsent,
	topOfMarketingSources,

	/** conversions */
	conversions,
	addToCartConversion,
	checkoutSalesFunnel,
	abandonedCarts,
	topOfProductsByAddingToFavorites,
	uniqueVisitorsProductViews,
	uniqueVisitorsProductInCart,
	uniqueVisitorsCheckoutStart,
	uniqueVisitorsPurchase,

}
