package com.isroot.echocaulator

data class EchoProduct(
    val unit: Int,
    val price: Int,
    var number: Int = 0
) {
    var efficiency: Float = 0.0f
    init {
       efficiency = price.toFloat()/unit.toFloat()
    }

    fun getCopied(): EchoProduct {
        val newProduct = EchoProduct(unit, price)
        newProduct.efficiency = efficiency
        newProduct.number = number

        return newProduct
    }
}
