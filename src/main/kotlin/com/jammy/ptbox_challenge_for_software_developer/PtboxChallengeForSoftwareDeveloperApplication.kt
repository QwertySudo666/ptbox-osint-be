package com.jammy.ptbox_challenge_for_software_developer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.web.config.EnableSpringDataWebSupport
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
class PtboxChallengeForSoftwareDeveloperApplication

fun main(args: Array<String>) {
    runApplication<PtboxChallengeForSoftwareDeveloperApplication>(*args)
}
