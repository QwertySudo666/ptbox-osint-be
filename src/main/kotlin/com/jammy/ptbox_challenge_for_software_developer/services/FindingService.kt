package com.jammy.ptbox_challenge_for_software_developer.services

import com.jammy.ptbox_challenge_for_software_developer.Finding
import com.jammy.ptbox_challenge_for_software_developer.FindingsFilter
import com.jammy.ptbox_challenge_for_software_developer.repositories.FindingRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class FindingService(
    private val findingRepository: FindingRepository,
) {
    fun searchFindings(findingsFilter: FindingsFilter, pageable: Pageable): Page<Finding> {
        val findings = findingRepository.searchFindings(findingsFilter.scanId, pageable)
        return findings.map { entity ->
            Finding(
                id = entity.id,
                type = entity.type,
                fqdn = entity.fqdn,
                ipAddress = entity.ipAddress,
                dnsType = entity.dnsType,
                dnsValue = entity.dnsValue,
                asn = entity.asn,
                netblock = entity.netblock
            )
        }
    }
}