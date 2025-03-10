package com.jammy.ptbox_challenge_for_software_developer.controllers

import com.jammy.ptbox_challenge_for_software_developer.Finding
import com.jammy.ptbox_challenge_for_software_developer.FindingsFilter
import com.jammy.ptbox_challenge_for_software_developer.services.FindingService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/findings")
class FindingController(
    private val findingService: FindingService
) {
    @PostMapping
    fun searchFindings(
        @RequestBody findingsFilter: FindingsFilter,
        @PageableDefault(page = 0, size = 20, sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable,
    ): ResponseEntity<Page<Finding>> {
        return ResponseEntity.ok(findingService.searchFindings(findingsFilter, pageable))
    }
}