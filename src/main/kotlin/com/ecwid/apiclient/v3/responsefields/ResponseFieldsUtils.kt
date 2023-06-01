package com.ecwid.apiclient.v3.responsefields

/**
 * count and limit required for correct work asSequence loop logic
 */
internal val AS_SEQUENCE_SEARCH_RESULT_REQUIRED_FIELDS = newResponseFields { fields("count", "limit") }
