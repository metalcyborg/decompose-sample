package com.example.common.list.models.domain

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class Breed(val name: String, val subBreed: String? = null): Parcelable
