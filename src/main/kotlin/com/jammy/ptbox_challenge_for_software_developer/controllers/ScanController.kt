package com.jammy.ptbox_challenge_for_software_developer.controllers

import com.jammy.ptbox_challenge_for_software_developer.ScanDetailsResponse
import com.jammy.ptbox_challenge_for_software_developer.ScanRequest
import com.jammy.ptbox_challenge_for_software_developer.ScanResponse
import com.jammy.ptbox_challenge_for_software_developer.services.ScanService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/scans")
class ScanController(private val scanService: ScanService) {

    @PostMapping
    fun startScan(@RequestBody request: ScanRequest): ResponseEntity<ScanResponse> {
        val scan = scanService.startScan(request.domain)
        return ResponseEntity.ok(scan)
    }

    @GetMapping
    fun searchScans(): ResponseEntity<List<ScanResponse>> {
        return ResponseEntity.ok(scanService.getAllScans())
    }

    @GetMapping("/{id}")
    fun getScan(@PathVariable id: UUID): ResponseEntity<ScanDetailsResponse> {
        return ResponseEntity.ok(scanService.getScanDetails(id))
    }

    @GetMapping("/{scanId}/stop")
    fun stopScan(@PathVariable scanId: UUID): String {
        scanService.stopAmassScan("amass_scan_$scanId")
        return "Scan $scanId has been cancelled."
    }
}