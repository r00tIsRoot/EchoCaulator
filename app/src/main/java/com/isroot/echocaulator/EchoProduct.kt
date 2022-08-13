package com.isroot.echocaulator

data class EchoProduct(
    val unit: Int,
    val price: Int
) {
    var efficiency: Float = 0.0f
    var number: Int = 0
    init {
       efficiency = price.toFloat()/unit.toFloat()
    }
}
