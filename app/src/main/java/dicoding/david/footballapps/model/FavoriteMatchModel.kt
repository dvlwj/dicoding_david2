package dicoding.david.footballapps.model

data class FavoriteMatchModel(
    var id: Long?,
    var idEvent: String?,
    var strHomeTeam: String?,
    var intHomeScore: String?,
    var strAwayTeam: String?,
    var intAwayScore: String?,
    var dateEvent: String?
){
    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val EVENT_ID: String = "EVENT_ID"
        const val HOME_TEAM: String = "HOME_TEAM"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val AWAY_TEAM: String = "AWAY_TEAM"
        const val AWAY_SCORE: String = "AWAY_SCORE"
        const val DATE_EVENT: String = "DATE_EVENT"

    }
}