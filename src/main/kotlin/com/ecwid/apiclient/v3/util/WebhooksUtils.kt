package com.ecwid.apiclient.v3.util

import com.ecwid.apiclient.v3.dto.webhook.Webhook
import org.apache.commons.codec.binary.Base64
import org.apache.commons.codec.digest.HmacAlgorithms
import org.apache.commons.codec.digest.HmacUtils

@Suppress("unused")
object WebhooksUtils {

    const val WEBHOOK_SIGNATURE_HEADER_NAME = "X-Ecwid-Webhook-Signature"

    /**
     * Webhook signature verification
     * @param signature - webhook signature obtained from the X-Ecwid-Webhook-Signature header
     * @param webhook - the webhook itself, for which you need to check the signature
     * @param clientSecret - clientSecret of the application for which the webhook was sent
     * @return true if the signature is valid
     */
	@JvmStatic
	fun isSignatureValid(signature: String?, webhook: Webhook?, clientSecret: String): Boolean {
        if (webhook == null || signature.isNullOrEmpty()) {
			return false
		}

        val bt = HmacUtils(HmacAlgorithms.HMAC_SHA_256, clientSecret)
            .hmac("${webhook.eventCreated}.${webhook.eventId}")
        val result = Base64.encodeBase64String(bt)

        return signature == result
    }

}
