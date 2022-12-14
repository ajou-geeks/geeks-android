package com.geeks.model

import android.os.Parcelable
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
):Parcelable

data class GetNoteRoomResponse(
    val totalCount: Int,
    val elements: List<NoteRoomModel>
)

@Parcelize
data class NoteRoomModel(
    val id: Int,
    val roomId: Int,
    val content: String,
    val senderInfo: OtherInfoModel,
    val receiverInfo: OtherInfoModel,
    val createdAt: String,
    val owner: Boolean
): Parcelable

data class SendNoteModel(
    val receiverId: Int,
    val content: String
)