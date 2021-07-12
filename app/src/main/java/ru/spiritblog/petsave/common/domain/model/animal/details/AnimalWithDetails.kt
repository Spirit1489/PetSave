package ru.spiritblog.petsave.common.domain.model.animal.details

import org.threeten.bp.LocalDateTime
import ru.spiritblog.petsave.common.domain.model.animal.AdoptionStatus
import ru.spiritblog.petsave.common.domain.model.animal.Media

data class AnimalWithDetails(
    val id: Long,
    val name: String,
    val type: String,
    val details: Details,
    val media: Media,
    val tags: List<String>,
    val adoptionStatus: AdoptionStatus,
    val publishedAt: LocalDateTime
)