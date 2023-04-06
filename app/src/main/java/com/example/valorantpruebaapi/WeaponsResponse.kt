package com.example.valorantpruebaapi

data class WeaponsResponse(
    val status: Int,
    val data: List<Weapon>
)

data class Weapon(
    val uuid: String,
    val displayName: String,
    val category: String,
    val defaultSkinUuid: String,
    val displayIcon: String,
    val killStreamIcon: String,
    val assetPath: String,
    val weaponStats: WeaponStats
)

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
    val altFireReloadTimeSeconds: Double?
)