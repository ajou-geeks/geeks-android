package com.geeks.model

import kotlinx.android.parcel.Parcelize

data class GetNoteResponse(
    val totalCount: Int,
    val elements: List<NoteListModel>
)

@Parcelize
data class NoteListModel(
    val id: Int,
    val recentNoteContent: String,
    val otherInfo: OtherInfoModel
)