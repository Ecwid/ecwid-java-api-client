package com.ecwid.apiclient.v3.converter

import com.ecwid.apiclient.v3.dto.common.LocalizedValueMap
import com.ecwid.apiclient.v3.dto.common.NullableUpdatedValue
import com.ecwid.apiclient.v3.dto.profile.request.UpdatedExtrafieldConfig
import com.ecwid.apiclient.v3.dto.profile.result.FetchedExtrafieldConfig

fun FetchedExtrafieldConfig.toUpdated(): UpdatedExtrafieldConfig {
	return UpdatedExtrafieldConfig(
		key = key,
		title = title,
		type = type,
		textPlaceholder = textPlaceholder,
		tip = tip,
		options = options?.map(FetchedExtrafieldConfig.FetchedExtrafieldOptionConfig::toUpdated),
		value = value,
		available = available,
		required = required,
		checkoutDisplaySection = checkoutDisplaySection,
		orderDetailsDisplaySection = orderDetailsDisplaySection,
		showForCountry = NullableUpdatedValue(showForCountry),
		showForPaymentMethodIds = NullableUpdatedValue(showForPaymentMethodIds),
		showForShippingMethodIds = NullableUpdatedValue(showForShippingMethodIds),
		showInInvoice = showInInvoice,
		showInNotifications = showInNotifications,
		orderBy = orderBy,
		surchargeType = NullableUpdatedValue(surchargeType),
		surchargeTaxable = surchargeTaxable,
		showZeroSurchargeInTotal = showZeroSurchargeInTotal,
		surchargeShortName = NullableUpdatedValue(surchargeShortName?.toUpdated()),
		titleTranslated = if (titleTranslated != null) LocalizedValueMap(titleTranslated) else null,
		textPlaceholderTranslated = if (textPlaceholderTranslated != null) LocalizedValueMap(textPlaceholderTranslated) else null,
		tipTranslated = if (tipTranslated != null) LocalizedValueMap(tipTranslated) else null,
		valueTranslated = if (valueTranslated != null) LocalizedValueMap(valueTranslated) else null
	)
}

fun FetchedExtrafieldConfig.FetchedExtrafieldOptionConfig.toUpdated(): UpdatedExtrafieldConfig.UpdatedExtrafieldOptionConfig {
	return UpdatedExtrafieldConfig.UpdatedExtrafieldOptionConfig(
		title = title,
		subtitle = subtitle,
		surcharge = surcharge,
		titleTranslated = if (titleTranslated != null) LocalizedValueMap(titleTranslated) else null,
		subtitleTranslated = if (subtitleTranslated != null) LocalizedValueMap(subtitleTranslated) else null
	)
}

fun FetchedExtrafieldConfig.FetchedExtrafieldSurchargeConfig.toUpdated(): UpdatedExtrafieldConfig.UpdatedExtrafieldSurchargeConfig {
	return UpdatedExtrafieldConfig.UpdatedExtrafieldSurchargeConfig(
		name = name,
		showSurchargePercentValue = showSurchargePercentValue,
		nameTranslated = if (nameTranslated != null) LocalizedValueMap(nameTranslated) else null
	)
}
