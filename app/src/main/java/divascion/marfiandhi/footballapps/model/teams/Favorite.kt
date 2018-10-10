package divascion.marfiandhi.footballapps.model.teams

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Marfiandhi on 10/6/2018.
 */

@Parcelize
data class Favorite(val id: Long?, val teamId: String?, val teamName: String?, val teamBadge: String?,
                    val teamFormedYear: String?, val teamStadium: String?): Parcelable {

    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val TEAM_ID: String = "TEAM_ID"
        const val TEAM_NAME: String = "TEAM_NAME"
        const val TEAM_BADGE: String = "TEAM_BADGE"
        const val TEAM_FORMED_YEAR: String = "TEAM_FORMED_YEAR"
        const val TEAM_STADIUM: String = "TEAM_STADIUM"
    }
}