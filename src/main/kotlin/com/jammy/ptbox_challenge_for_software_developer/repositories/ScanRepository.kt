package com.jammy.ptbox_challenge_for_software_developer.repositories

import com.jammy.ptbox_challenge_for_software_developer.ScanEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ScanRepository : JpaRepository<ScanEntity, UUID>
