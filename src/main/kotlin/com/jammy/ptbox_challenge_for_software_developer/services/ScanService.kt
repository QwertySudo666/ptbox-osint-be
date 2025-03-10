package com.jammy.ptbox_challenge_for_software_developer.services

import com.jammy.ptbox_challenge_for_software_developer.ScanDetailsResponse
import com.jammy.ptbox_challenge_for_software_developer.ScanEntity
import com.jammy.ptbox_challenge_for_software_developer.ScanResponse
import com.jammy.ptbox_challenge_for_software_developer.repositories.ScanRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*


@Service
class ScanService(
    private val scanRepository: ScanRepository,
    private val amassScanJob: AmassScanJob
) {

    fun startScan(domain: String, timeoutMinutes: Long = 10): ScanResponse {
        val id = UUID.randomUUID()
        val startTime = LocalDateTime.now()
        val scanEntity = scanRepository.save(
            ScanEntity(
                id = id,
                domain = domain,
                startTime = startTime,
                endTime = null,
                status = "IN_PROGRESS",
                containerName = "amass_scan_$id"
            )
        )

        amassScanJob.runAmassScan(scanEntity)
        return ScanResponse(
            scanEntity.id,
            scanEntity.domain,
            scanEntity.startTime,
            scanEntity.endTime,
            scanEntity.status
        )
    }

    fun stopAmassScan(containerName: String) {
        ProcessBuilder("docker", "stop", containerName).start().waitFor()
    }

    fun getAllScans(): List<ScanResponse> {
        return scanRepository.findAll().map {
            ScanResponse(it.id, it.domain, it.startTime, it.endTime, it.status)
        }
    }

    fun getScanDetails(id: UUID): ScanDetailsResponse {
        val scan = scanRepository.findById(id).orElseThrow { RuntimeException("Scan not found") }
        return ScanDetailsResponse(scan.id, scan.domain, scan.startTime, scan.endTime, scan.status)
    }
}