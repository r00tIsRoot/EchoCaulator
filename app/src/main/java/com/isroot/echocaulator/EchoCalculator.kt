package com.isroot.echocaulator

import android.util.Log

class EchoCalculator {
    val originProducts: ArrayList<EchoProduct> = ArrayList()
    var productsSortedByEfficiency: ArrayList<EchoProduct> = ArrayList()
    var productsSortedByUnit: ArrayList<EchoProduct> = ArrayList()
    lateinit var biggestUnit:EchoProduct

    init {
        originProducts.add(EchoProduct(723, 14000))
        originProducts.add(EchoProduct(6918, 140000))
        originProducts.add(EchoProduct(2123, 44000))
        originProducts.add(EchoProduct(3498, 75000))
        originProducts.add(EchoProduct(194, 4200))
        originProducts.add(EchoProduct(320, 7000))
        originProducts.add(EchoProduct(60, 1500))
    }

    fun calc(targetEcho: Int){
        findBiggestProduct()
        productsSortedByEfficiency.addAll(originProducts.sortedBy(EchoProduct::efficiency))
        productsSortedByUnit.addAll(originProducts.sortedBy(EchoProduct::unit))
    }

    fun findBiggestProduct(){
        biggestUnit = EchoProduct(0,0)
        for(echoProduct in originProducts){
            if(biggestUnit.unit < echoProduct.unit){
                biggestUnit = echoProduct
            }
        }
    }
    fun findBestEfficiencyIndex(products: ArrayList<EchoProduct>): Int{
        var resultIndex = 0
        val smallestEfficiency: Float = products[resultIndex].efficiency
        var forIndex = 0
        for(product in products) {
            if(smallestEfficiency > product.efficiency){
                resultIndex = forIndex
            }
            forIndex+=1
        }

        return resultIndex
    }
}