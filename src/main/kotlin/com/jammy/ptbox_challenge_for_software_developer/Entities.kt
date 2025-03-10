package com.jammy.ptbox_challenge_for_software_developer

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "scans")
data class ScanEntity(
    @Id val id: UUID,
    val domain: String,
    val startTime: LocalDateTime,
    var endTime: LocalDateTime?,
    var status: String,
    val containerName: String
)

@Entity
@Table(name = "findings")
data class FindingEntity(
    @Id
    val id: UUID = UUID.randomUUID(),
    @ManyToOne
    @JoinColumn(name = "scan_id", nullable = false)
    val scan: ScanEntity,
    @Enumerated(EnumType.STRING)
    val type: ScanResultType,
    val fqdn: String? = null,
    val ipAddress: String? = null,
    val dnsType: String? = null,
    val dnsValue: String? = null,
    val asn: Int? = null,
    val netblock: String? = null
)
