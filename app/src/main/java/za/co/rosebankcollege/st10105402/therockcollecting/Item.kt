package za.co.rosebankcollege.st10105402.therockcollecting

data class Item(
    var itemName: String = "",
    var description: String = "",
    var purchaseDate: String = "",
    var price: Double = 0.0,
    var category: String = "",
    var imageUrl: String = "",
    var isForSale: Boolean = false // Add the isForSale property with a default value
) {
    // default constructor
    constructor() : this("", "", "", 0.0, "")
}
