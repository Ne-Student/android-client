package com.studa.android.client.api.dto

import com.google.gson.annotations.SerializedName
import java.util.*

data class TeacherDTO(
    var id: UUID?,

    @SerializedName("associated_account_id")
    var associatedAccountId: UUID?,

    @SerializedName("first_name")
    var firstName: String?,

    @SerializedName("last_name")
    var lastName: String?
)