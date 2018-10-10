package divascion.marfiandhi.footballapps.model.players

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Marfiandhi on 10/10/2018.
 */
@Parcelize
class Player(
        @SerializedName("strPlayer")
        var playerName: String? = null,

        @SerializedName("strFanart1")
        var playerFanArt: String? = null,

        @SerializedName("strCutout")
        var playerCutOut: String? = null,

        @SerializedName("strPosition")
        var playerPosition: String? = null,

        @SerializedName("strDescriptionEN")
        var playerDescription: String? = null,

        @SerializedName("strWeight")
        var playerWeight: String? = null,

        @SerializedName("strHeight")
        var playerHeight: String? = null,

        @SerializedName("strThumb")
        var playerThumbnail: String? = null
): Parcelable