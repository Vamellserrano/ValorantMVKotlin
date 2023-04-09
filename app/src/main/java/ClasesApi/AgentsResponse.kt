package ClasesApi

import android.os.Parcelable

data class AgentsResponse (
    val status: Int,
    val data: List<Agent>
    )
@kotlinx.parcelize.Parcelize
data class Agent(
    val uuid: String,
    val displayName: String,
    val description: String,
    val developerName: String,
    val characterTags: List<String>,
    val displayIcon: String,
    val displayIconSmall: String,
    val bustPortrait: String,
    val fullPortrait: String,
    val fullPortraitV2: String,
    val killfeedPortrait: String,
    val background: String,
    val backgroundGradientColors: List<String>,
    val assetPath: String,
    val isFullPortraitRightFacing: Boolean,
    val isPlayableCharacter: Boolean,
    val isAvailableForTest: Boolean,
    val isBaseContent: Boolean,
    val role: Role,
    val abilities: List<Ability>,
): Parcelable {
    override fun toString(): String {
        return "Agent(uuid='$uuid', displayName='$displayName')"
    }
}
@kotlinx.parcelize.Parcelize
data class Role(
    val uuid: String,
    val displayName: String,
    val description: String,
    val displayIcon: String,
    val assetPath: String,
) : Parcelable
@kotlinx.parcelize.Parcelize
data class Ability(
    val slot: String,
    val displayName: String,
    val description: String,
    var displayIcon: String,
) : Parcelable

