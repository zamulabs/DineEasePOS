package com.zamulabs.dineeasepos.order.neworder

import androidx.compose.runtime.Immutable

@Immutable
data class NewOrderUiState(
    val tables: List<String> = listOf("Select Table"),
    val selectedTable: String = "Select Table",
    val searchString: String = "",
    val categories: List<String> = listOf("Appetizers","Main Courses","Desserts","Drinks","Specials"),
    val selectedCategoryIndex: Int = 0,
    val items: List<MenuItem> = sampleItems(),
    val cart: List<CartItem> = listOf(
        CartItem("Crispy Calamari", 2, 14.00),
        CartItem("Spinach Artichoke Dip", 1, 12.00),
    ),
    val notes: String = "",
){
    val subtotal: Double get() = cart.sumOf { it.quantity * it.price }
    val tax: Double get() = (subtotal * 0.08).let { Math.round(it*100.0)/100.0 }
    val total: Double get() = Math.round((subtotal + tax)*100.0)/100.0
}

@Immutable
data class MenuItem(
    val title: String,
    val description: String,
    val imageUrl: String,
)

@Immutable
data class CartItem(
    val title: String,
    val quantity: Int,
    val price: Double,
)

private fun sampleItems(): List<MenuItem> = listOf(
    MenuItem("Crispy Calamari","Tender calamari, lightly fried, served with marinara sauce.","https://lh3.googleusercontent.com/aida-public/AB6AXuAGQpRkJgRLXHpbO4zfJE5cOBEb71QVWbcpYwGqiXColH_pyPOhQjDVtROEvNQxNhEYvGnUuroTY8JGqFJS_q35JqmwtwygMX1kOMJnIMiYEd97rdxQj29elYGzc8nZIM35n0OM24Yu2K0oEXwwLc2Up1FfPM4F90jn4EWXSlRn0aV76gmsaozJKH2LEAunAvK2bheezNDKiUqmz93rGI-BZdl7PkVQ5q6RJ_CiH4Ihn4utc3cmrmq0nXzJB_ARlZ9_f_kgx9K6kFI"),
    MenuItem("Spinach Artichoke Dip","Creamy dip with spinach, artichoke, and cheese, served with tortilla chips.","https://lh3.googleusercontent.com/aida-public/AB6AXuAqiJ8xAcqqNNBDYOtdHRGBMTVQS-c-XNg-NdmMSnIb2Xr4b0yiEgcj09gJFue-uKfIqMLkDoTRyJAGjsAZpAK57mbtbU7xWCl1LzNkaed48ZLNTa_u1roEdRT1OdhrU-5L4W4yIcOY2lgXjgAVm0TF3aoEDYVK0pgu7MrunkfbWgscvXGTy0TxZXL4_aE-fHMaRvvXxlRYREwUHBUQSPDSNqYIKK4fJORdVEwl8nobIsR2p2c-QrMd1ccux1ud1D9g4DyEcypu1ts"),
    MenuItem("Bruschetta Trio","Toasted baguette slices topped with tomato basil, mushroom truffle, and roasted pepper goat cheese.","https://lh3.googleusercontent.com/aida-public/AB6AXuAfjmsuEYFHewjCkyNhAIREBAAKdyeUuTa7bHWrOZZznf9gdDftDTF5oIPXwak1ISKu5zeldeHu5d4f_80jQ17nz-oWUDYKBuEAFRLAOTwWpSF4PMu1ECJ285E7Szp3rI7I6QC89KlfgrIKJjFYAQBO1DYY-owNjbF4-zYW9RIM0oREDIM0bG_gOOICxYMbi3pW3EABKHkYybflo-5NgImpSkK9nQ8ywjBbwXE9hBfgk1ohw1oxXAEfbmMf2elFALtxU8R4hlPs1hk"),
    MenuItem("Shrimp Ceviche","Fresh shrimp marinated in lime juice, cilantro, and red onion.","https://lh3.googleusercontent.com/aida-public/AB6AXuADXLXTojd2ymPruyFibH68EWlvp0kjhzW5TRetWtrKqRPE_zOgjhbomnfrbpxLnPzkN3vLNXqIqYvU7qbIe9TYzVR7wUfKsP2GGUjqAcyDCKB9jRAprhO0znoT1wv5H6yAop6BfJrcCOJfaxN7rct1YU38nXwyacMq2rdVM6oyF1HrPXKat8APHnbCk9hNsPJP3fYJgf8dLKC46udMoEsJgBQ_EZcc1lxmf08FBtZlLuzWUK01lBpidPb0WgVFMUhiqW_ccQB6dx4"),
    MenuItem("Stuffed Mushrooms","Mushroom caps filled with herbed cream cheese, baked to perfection.","https://lh3.googleusercontent.com/aida-public/AB6AXuDtyvAdJUzCfz00aNGpnSyH3fjk-oxEskCdfogqSOlbHGUrym8ddztlc77aySgVcoklktY08V8Gxf0iCT4KsVOIn91ah2qihahy_z1GQ7pposEGB1NOtIJ7Z1A2slIrRpzmqgfWR2sZg2noJQI18wvL21G6-E2t1xkQ94WBkzNURpqeiSPLCQp0HDjiip1BR0Ej6pS6o2lALkdOkmlUvgTYgXWa55HVogew_GaR5_t81MYUX9qAQbXygaHUvnXeqP_9ar1bFWCdAmk"),
    MenuItem("Caprese Salad","Fresh mozzarella, tomatoes, and basil, drizzled with balsamic glaze.","https://lh3.googleusercontent.com/aida-public/AB6AXuBRmMBcdOx_6_9ZGucEhtT9-Ff4AVKFTKN1kUCiGDCecWTcHZZ-O0OQCXzDK33lXzPdtotu2_HpQdFVM2_eW-t9QDLzxaiD7sUjlqBx3V-jq0wpxSbpsLMxEN0COotq5Hn2YAtTHT4G5e93AbLnGBbNFyXx5FD2yQfp88gDSfPVVnfhQEH8AMpfFXganCrij8dYzMMOGPSgC8K_iSRy2sQlh3SY-AoshDB6V_yTb9_Ar-l12EuNSEO0CnscV-mXxePgPBKxrOwkxkA"),
)