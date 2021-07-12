package ru.spiritblog.petsave.common.domain.model.pagination

import ru.spiritblog.petsave.common.domain.model.animal.details.AnimalWithDetails

data class PaginatedAnimals(
    val animals: List<AnimalWithDetails>,
    val pagination: Pagination
)