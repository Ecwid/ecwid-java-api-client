package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.profile.result.FetchedExtrafieldConfig
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable

val fetchedExtrafieldConfigNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
	AllowNullable(FetchedExtrafieldConfig::type),
	AllowNullable(FetchedExtrafieldConfig::textPlaceholder),
	AllowNullable(FetchedExtrafieldConfig::tip),
	AllowNullable(FetchedExtrafieldConfig::options),
	AllowNullable(FetchedExtrafieldConfig::value),
	AllowNullable(FetchedExtrafieldConfig::available),
	AllowNullable(FetchedExtrafieldConfig::required),
	AllowNullable(FetchedExtrafieldConfig::orderDetailsDisplaySection),
	AllowNullable(FetchedExtrafieldConfig::showForCountry),
	AllowNullable(FetchedExtrafieldConfig::showForPaymentMethodIds),
	AllowNullable(FetchedExtrafieldConfig::showForShippingMethodIds),
	AllowNullable(FetchedExtrafieldConfig::showInInvoice),
	AllowNullable(FetchedExtrafieldConfig::showInNotifications),
	AllowNullable(FetchedExtrafieldConfig::orderBy),
	AllowNullable(FetchedExtrafieldConfig::surchargeType),
	AllowNullable(FetchedExtrafieldConfig::surchargeTaxable),
	AllowNullable(FetchedExtrafieldConfig::showZeroSurchargeInTotal),
	AllowNullable(FetchedExtrafieldConfig::surchargeShortName),
	AllowNullable(FetchedExtrafieldConfig::titleTranslated),
	AllowNullable(FetchedExtrafieldConfig::textPlaceholderTranslated),
	AllowNullable(FetchedExtrafieldConfig::tipTranslated),
	AllowNullable(FetchedExtrafieldConfig::valueTranslated),

	AllowNullable(FetchedExtrafieldConfig.FetchedExtrafieldOptionConfig::subtitle),
	AllowNullable(FetchedExtrafieldConfig.FetchedExtrafieldOptionConfig::subtitleTranslated),
	AllowNullable(FetchedExtrafieldConfig.FetchedExtrafieldOptionConfig::surcharge),
	AllowNullable(FetchedExtrafieldConfig.FetchedExtrafieldOptionConfig::title),
	AllowNullable(FetchedExtrafieldConfig.FetchedExtrafieldOptionConfig::titleTranslated),
	AllowNullable(FetchedExtrafieldConfig.FetchedExtrafieldSurchargeConfig::name),
	AllowNullable(FetchedExtrafieldConfig.FetchedExtrafieldSurchargeConfig::nameTranslated),
	AllowNullable(FetchedExtrafieldConfig.FetchedExtrafieldSurchargeConfig::showSurchargePercentValue),
)
