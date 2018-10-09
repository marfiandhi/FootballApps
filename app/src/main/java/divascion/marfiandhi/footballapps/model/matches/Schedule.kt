package divascion.marfiandhi.footballapps.model.matches

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Created by Marfiandhi on 10/7/2018.
 */
@Parcelize
data class Schedule (
        @SerializedName("strHomeTeam")
        val home: String? = null,

        @SerializedName("strAwayTeam")
        val away: String? = null,

        @SerializedName("intHomeScore")
        val homeScore: Int? = null,

        @SerializedName("intAwayScore")
        val awayScore: Int? = null,

        @SerializedName("dateEvent")
        val date: Date? = null,

        @SerializedName("strHomeGoalDetails")
        val homeGoalDetails: String? = null,

        @SerializedName("strAwayGoalDetails")
        val awayGoalDetails: String? = null,

        @SerializedName("intHomeShots")
        val homeShots: Int? = null,

        @SerializedName("intAwayShots")
        val awayShots: Int? = null,

        @SerializedName("strHomeLineupGoalkeeper")
        val homeGoalKeeper: String? = null,

        @SerializedName("strHomeLineupDefense")
        val homeDefense: String? = null,

        @SerializedName("strHomeLineupMidfield")
        val homeMidfield: String? = null,

        @SerializedName("strHomeLineupForward")
        val homeForward: String? = null,

        @SerializedName("strHomeLineupSubstitutes")
        val homeSubstitutes: String? = null,

        @SerializedName("strAwayLineupGoalkeeper")
        val awayGoalKeeper: String? = null,

        @SerializedName("strAwayLineupDefense")
        val awayDefense: String? = null,

        @SerializedName("strAwayLineupMidfield")
        val awayMidfield: String? = null,

        @SerializedName("strAwayLineupForward")
        val awayForward: String? = null,

        @SerializedName("strAwayLineupSubstitutes")
        val awaySubstitutes: String? = null,

        @SerializedName("idHomeTeam")
        val idHome: String? = null,

        @SerializedName("idAwayTeam")
        val idAway: String? = null,

        @SerializedName("idEvent")
        val idEvent: String? = null,

        @SerializedName("strTime")
        val strTime: String? = null

) : Parcelable