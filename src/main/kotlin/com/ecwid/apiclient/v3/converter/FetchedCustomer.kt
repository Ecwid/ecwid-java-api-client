package com.ecwid.apiclient.v3.converter

import com.ecwid.apiclient.v3.dto.customer.request.UpdatedCustomer
import com.ecwid.apiclient.v3.dto.customer.result.FetchedCustomer

fun FetchedCustomer.toUpdated(): UpdatedCustomer {
	return UpdatedCustomer(
		email = email,
		customerGroupId = customerGroupId,
		billingPerson = billingPerson?.toUpdated(),
		shippingAddresses = shippingAddresses?.map(FetchedCustomer.ShippingAddress::toUpdated),
		contacts = contacts?.map(FetchedCustomer.CustomerContact::toUpdated),
		taxId = taxId,
		taxIdValid = taxIdValid,
		taxExempt = taxExempt,
		acceptMarketing = acceptMarketing,
		lang = lang,
		privateAdminNotes = privateAdminNotes,
		commercialRelationshipScheme = commercialRelationshipScheme,
	)
}

fun FetchedCustomer.BillingPerson.toUpdated(): UpdatedCustomer.BillingPerson {
	return UpdatedCustomer.BillingPerson(
		name = name,
		companyName = companyName,
		street = street,
		city = city,
		countryCode = countryCode,
		postalCode = postalCode,
		stateOrProvinceCode = stateOrProvinceCode,
		phone = phone
	)
}

fun FetchedCustomer.ShippingAddress.toUpdated(): UpdatedCustomer.ShippingAddress {
	return UpdatedCustomer.ShippingAddress(
		id = id,
		name = name,
		companyName = companyName,
		street = street,
		city = city,
		countryCode = countryCode,
		postalCode = postalCode,
		stateOrProvinceCode = stateOrProvinceCode,
		phone = phone,
		note = note,
		defaultAddress = defaultAddress,
		orderBy = orderBy,
	)
}

fun FetchedCustomer.CustomerContact.toUpdated(): UpdatedCustomer.CustomerContact {
	return UpdatedCustomer.CustomerContact(
		id = id,
		contact = contact,
		handle = handle,
		note = note,
		type = type,
		default = default,
		orderBy = orderBy,
	)
}
