package ClasesApi

//Class "MapsResponse" which is linked directly to ValorantService interface. This only contains
//the attributes of the classes and direct subclasses.

data class MapsResponse(
    val status: Int,
    val data: List<Map>
)

data class Map(
    val uuid: String,
    val displayName: String,
    val coordinates: String,
    val displayIcon: String,
    val listViewIcon: String,
    val splash: String,
    val assetPath: String,
    val mapUrl: String,
    val xMultiplier: Float,
    val yMultiplier: Float,
    val xScalarToAdd: Float,
    val yScalarToAdd: Float,
    val callouts: List<Callout>
)

data class Callout(
    val regionName: String,
    val superRegionName: String,
    val location: Location
)

data class Location(
    val x: Float,
    val y: Float
)