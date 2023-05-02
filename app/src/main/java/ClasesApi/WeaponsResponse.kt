package ClasesApi

import android.os.Parcelable

//Class "WeaponsResponse" which is linked directly to ValorantService interface. This only contains
//the attributes of the classes and direct subclasses.

data class WeaponsResponse(
    val status: Int,
    val data: List<Weapon>
)

@kotlinx.parcelize.Parcelize
data class Weapon(
    val uuid: String,
    val displayName: String,
    val category: String,
    val defaultSkinUuid: String,
    val displayIcon: String,
    val killStreamIcon: String,
    val assetPath: String,
    val weaponStats: WeaponStats?,
    /* adsStats: AdsStats,
    val altShotgunStats: AltShotgunStats,
    val airBurstStats: AirBurstStats,*/
    val shopData: ShopData?,
    /*val skins: List<Skin>,
    val chromas: List<Chroma>,
    val levels: List<Level>*/
): Parcelable
@kotlinx.parcelize.Parcelize
data class WeaponStats(
    val fireRate: Double,
    val magazineSize: Int,
    val runSpeedMultiplier: Double,
    val equipTimeSeconds: Double,
    val reloadTimeSeconds: Double,
    val wallPenetration: String,
    val wallPenetrationUnarmored: String,
    val altFireType: String?,
    val altFireRate: Double?,
    val altFireSpread: Double?,
    val altFireMagazineSize: Int?,
    val altFireEquipTimeSeconds: Double?,
    val altFireReloadTimeSeconds: Double?,
    val damageRanges: List<DamageRanges>?,
): Parcelable

@kotlinx.parcelize.Parcelize
data class ShopData(
    val cost: Int,
    val category: String,
    val categoryText: String,
    val gridPosition: GridPosition,
    val row: Int,
    val column: Int,
    val canBeTrashed: Boolean,
    val image: String,
    val newImage: String,
    val newImage2: String,
    val assetPath: String
): Parcelable

@kotlinx.parcelize.Parcelize
data class GridPosition(
    val row: Int,
    val column: Int
): Parcelable

@kotlinx.parcelize.Parcelize
data class DamageRanges(
    val rangeStartMeters: Float,
    val rangeEndMeters: Float,
    val headDamage: Float,
    val bodyDamage: Float,
    val legDamage: Float
) : Parcelable