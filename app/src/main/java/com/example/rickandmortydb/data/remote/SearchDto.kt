package com.example.rickandmortydb.data.remote

import com.squareup.moshi.Json

data class SearchDto(
    @field:Json(name = "info")
    val metaData: SearchMetadataDto,

    @field:Json(name = "results")
    val results: List<CharacterDto>
)

data class SearchMetadataDto(
    val count : Int = 0,

    @field:Json(name = "next")
    val nextPage: String?
)
