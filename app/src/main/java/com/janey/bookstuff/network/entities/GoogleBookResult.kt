package com.janey.bookstuff.network.entities

data class GoogleBookResult(
    val items: List<Item>,
    val totalItems: Int
)

data class Item(
    val volumeInfo: VolumeInfo
)

data class VolumeInfo(
    val authors: List<String>,
    val imageLinks: ImageLinks?,
    val title: String,
    val pageCount: Int?,
    val publishedDate: String?,
    val description: String?,
)

data class ImageLinks(
    val smallThumbnail: String,
    val thumbnail: String
)