package com.jammy.ptbox_challenge_for_software_developer.repositories

import com.jammy.ptbox_challenge_for_software_developer.FindingEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface FindingRepository : JpaRepository<FindingEntity, UUID> {
    @Query(
        """
        SELECT f.* from findings f
        WHERE (:scanId IS NULL OR f.scan_id = :scanId)
    """, nativeQuery = true
    )
    fun searchFindings(scanId: UUID?, pageable: Pageable): Page<FindingEntity>
}