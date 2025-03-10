package com.jammy.ptbox_challenge_for_software_developer

import java.time.LocalDateTime
import java.util.*


data class FindingsFilter(
    val scanId: UUID? = null,
)

data class ScanRequest(
    val domain: String
)

data class ScanResponse(
    val id: UUID,
    val domain: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime?,
    val status: String
)

data class ScanDetailsResponse(
    val id: UUID,
    val domain: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime?,
    val status: String
)

data class Finding(
    val id: UUID = UUID.randomUUID(),
    val type: ScanResultType,
    val fqdn: String? = null,
    val ipAddress: String? = null,
    val dnsType: String? = null,
    val dnsValue: String? = null,
    val asn: Int? = null,
    val netblock: String? = null
)

enum class ScanResultType {
    SUBDOMAIN,
    IP_ADDRESS,
    DNS_RECORD,
    ASN,
    NETBLOCK
}