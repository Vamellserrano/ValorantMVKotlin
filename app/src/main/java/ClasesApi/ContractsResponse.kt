package ClasesApi

//Class "ContractsResponse" which is linked directly to ValorantService interface. This only contains
//the attributes of the classes and direct subclasses.

data class ContractsResponse (
    val status: Int,
    val data: List<Contract>
)

data class Contract(
    val uuid: String,
    val displayName: String,
    val displayIcon: String,
    val shipIt: Boolean,
    val freeRewardScheduleUuid: String,
    val content: Content,
    val assetPath: String
)
    data class Content(
        val relationType: String,
        val relationUuid: String,
        val chapters: Array<Chapter>,
    )
        data class Chapter(
            val isEpilogue: Boolean,
            val levels: Array<Level>,
            val freeRewards: Array<FreeReward>,
            val premiumRewardScheduleUuid: String,
            val premiumVPCost: Int,
        )
            data class Level(
                val reward: Reward,
                val xp: Int,
                val vpCost: Int,
                val isPurchasableWithVP: Boolean
            )
                data class Reward(
                    val type: String,
                    val uuid: String,
                    val amount: Int,
                    val isHighlighted: Boolean,
                )
            data class FreeReward(
                val type: String,
                val uuid: String,
                val amount: Int,
                val isHighlighted: Boolean,
            )
