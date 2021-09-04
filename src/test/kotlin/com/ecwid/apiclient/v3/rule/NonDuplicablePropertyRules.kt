package com.ecwid.apiclient.v3.rule

import com.ecwid.apiclient.v3.dto.customer.request.UpdatedCustomer
import com.ecwid.apiclient.v3.dto.order.request.UpdatedOrder
import com.ecwid.apiclient.v3.dto.product.request.UpdatedProduct
import com.ecwid.apiclient.v3.dto.variation.request.UpdatedVariation
import com.ecwid.apiclient.v3.rule.NonDuplicablePropertyRule.PartialDuplicable
import com.ecwid.apiclient.v3.rule.NonDuplicablePropertyRule.WriteOnly
import kotlin.reflect.KProperty1

val nonDuplicablePropertyRules: List<NonDuplicablePropertyRule<*, *>> = listOf(

	// We should not copy this field if OrderItemSelectedOption.type is CHOICES
	PartialDuplicable(UpdatedOrder.OrderItemSelectedOption::value),

	WriteOnly(UpdatedCustomer::password),
	WriteOnly(UpdatedProduct.AttributeValue::alias),
	WriteOnly(UpdatedProduct.AttributeValue::name),
	WriteOnly(UpdatedVariation.AttributeValue::alias),
	WriteOnly(UpdatedVariation.AttributeValue::name),

)

sealed class NonDuplicablePropertyRule<T, R>(
	val property: KProperty1<T, R>
) {

	class WriteOnly<T, R> internal constructor(
		property: KProperty1<T, R>
	) : NonDuplicablePropertyRule<T, R>(property)

	class PartialDuplicable<T, R> internal constructor(
		property: KProperty1<T, R>
	) : NonDuplicablePropertyRule<T, R>(property)
}
