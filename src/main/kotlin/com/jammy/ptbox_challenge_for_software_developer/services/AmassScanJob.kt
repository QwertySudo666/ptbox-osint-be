package com.jammy.ptbox_challenge_for_software_developer.services

import com.jammy.ptbox_challenge_for_software_developer.Finding
import com.jammy.ptbox_challenge_for_software_developer.FindingEntity
import com.jammy.ptbox_challenge_for_software_developer.ScanEntity
import com.jammy.ptbox_challenge_for_software_developer.ScanResultType
import com.jammy.ptbox_challenge_for_software_developer.repositories.FindingRepository
import com.jammy.ptbox_challenge_for_software_developer.repositories.ScanRepository
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class AmassScanJob(
    private val findingRepository: FindingRepository,
    private val scanRepository: ScanRepository
) {
    @Async
    fun runAmassScan(scan: ScanEntity) {
        val processBuilder = ProcessBuilder(
            "docker", "run", "--rm", "--name", scan.containerName, "caffix/amass", "enum", "-d", scan.domain
        )

        try {
            val process = processBuilder.start()
            val reader = process.inputStream.bufferedReader()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                println(line)
                val finding = parseAmassOutput(line!!)
                findingRepository.save(
                    FindingEntity(
                        id = finding.id,
                        scan = scan,
                        type = finding.type,
                        fqdn = finding.fqdn,
                        ipAddress = finding.ipAddress,
                        dnsType = finding.dnsType,
                        dnsValue = finding.dnsValue,
                        asn = finding.asn,
                        netblock = finding.netblock,
                    )
                )
            }
            scan.endTime = LocalDateTime.now()
            scan.status = "FINISHED"
            scanRepository.save(scan)
            println("PROCESS FOR CONTAINER ${scan.containerName} WAS FINISHED")
        } catch (e: Exception) {
            scan.status = "FAILED"
            scanRepository.save(scan)
            println(e.message)
            e.printStackTrace()
        }
    }

    private fun parseAmassOutput(line: String): Finding {
        return if (line.contains("-->")) {
            val parts = line.split("-->").map { it.trim() }
            if (parts.size == 3) {
                Finding(
                    type = ScanResultType.DNS_RECORD,
                    fqdn = parts[0],
                    dnsType = parts[1].split("_")[0],
                    dnsValue = parts[2]
                )
            } else throw IllegalArgumentException("Invalid format")
        } else if (line.matches(Regex("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}"))) {
            Finding(type = ScanResultType.IP_ADDRESS, ipAddress = line)
        } else if (line.matches(Regex(".*\\(FQDN\\)"))) {
            Finding(type = ScanResultType.SUBDOMAIN, fqdn = line.replace(Regex("\\s*\\(FQDN\\)"), ""))
        } else if (line.matches(Regex("\\d+\\s*\\(ASN\\)"))) {
            Finding(type = ScanResultType.ASN, asn = line.replace(Regex("\\s*\\(ASN\\)"), "").toInt())
        } else if (line.matches(Regex("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}/\\d{1,2}\\s*\\(Netblock\\)"))) {
            Finding(
                type = ScanResultType.NETBLOCK,
                netblock = line.replace(Regex("\\s*\\(Netblock\\)"), "")
            )
        } else throw IllegalArgumentException("Unknown type for response: $line")
    }
}