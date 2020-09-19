package com.studa.android.client.api.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Teacher(
    var id: UUID? = null,

    @SerializedName("associated_account_id")
    var associatedAccountId: UUID? = null,

    @SerializedName("first_name")
    var firstName: String? = null,

    @SerializedName("last_name")
    var lastName: String? = null
)