package com.example.common.list.domain

import com.example.common.common.domain.BreedRepository
import com.example.common.list.models.domain.Breed

class BreedInteractor(private val repository: BreedRepository) {

    suspend fun getBreeds(): List<Breed> {
        return repository.getBreedList()
    }
}