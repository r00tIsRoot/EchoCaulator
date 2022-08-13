package com.isroot.echocaulator

import android.util.Log
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.roundToInt

class EchoCalculator{
    val originProducts: HashMap<Int, EchoProduct> = HashMap()
    var productsSortedByEfficiency: ArrayList<EchoProduct> = ArrayList()
    var productsSortedByUnit: ArrayList<EchoProduct> = ArrayList()
    var productsSortedByPrice: ArrayList<EchoProduct> = ArrayList()
    lateinit var biggestUnit:EchoProduct
    val maximumByProduct: HashMap<Int, Int> = HashMap()

    private val resultHM: HashMap<Int, Int> = HashMap()
    private var results: ArrayList<ArrayList<EchoProduct>> = ArrayList()
    private var result: HashMap<Int, EchoProduct> = HashMap()

    init {
        originProducts.put(723, EchoProduct(723, 14000))
        originProducts.put(6918, EchoProduct(6918, 140000))
        originProducts.put(2123, EchoProduct(2123, 44000))
        originProducts.put(3498, EchoProduct(3498, 75000))
        originProducts.put(194, EchoProduct(194, 4200))
        originProducts.put(320, EchoProduct(320, 7000))
        originProducts.put(60, EchoProduct(60, 1500))

        findBiggestProduct()
        productsSortedByEfficiency.addAll(originProducts.values.sortedBy(EchoProduct::efficiency))
        productsSortedByUnit.addAll(originProducts.values.sortedBy(EchoProduct::unit))
        productsSortedByPrice.addAll(originProducts.values.sortedBy(EchoProduct::price))

        initResult()
    }

    private fun initResult() {
        for (product in productsSortedByUnit) {
            resultHM.put(product.unit, 0)
            result.put(product.unit, product)
        }
        results = ArrayList()
        //result = HashMap()
    }

    fun calc(targetEcho: Int){
        initResult()
        //최대효율제품으로 가격이 가장큰 제품의 가격보다는 크게 할당
        val minorTarget = targetEcho - productsSortedByPrice[productsSortedByPrice.size-1].unit
        val bestEfficiencyProduct = productsSortedByEfficiency[0]
        val quotient = minorTarget / bestEfficiencyProduct.unit
        var minorTargetEcho = targetEcho
        if(quotient > 0) {
            resultHM[bestEfficiencyProduct.unit] = quotient
            minorTargetEcho = targetEcho - (bestEfficiencyProduct.unit * quotient)
        }

        calcRemainderMostEfficient(minorTargetEcho)
//        var finalEcho = 0
//        var finalPrice = 0
//        for(hashMapKey in resultHM.keys) {
//            Log.d("dodo", "result key is ${hashMapKey}, value is ${resultHM[hashMapKey]}")
//            for(product in originProducts.values) {
//                if(product.unit == hashMapKey){
////                    Log.d("dodo", "품목가격은 ${product.price}, 갯수는 ${result[hashMapKey]}")
//                    finalEcho += hashMapKey*resultHM[hashMapKey]!!
//                    finalPrice += (product.price*resultHM[hashMapKey]!!.toInt())
//                    break
//                }
//            }
//        }
//        Log.d("dodo", "final price is $finalPrice, echo is $finalEcho")
//        Log.d("dodo", "")

        calcRemainder(minorTargetEcho)
        var finalEcho = 0
        var finalPrice = 0
        for(productKey in result.keys) {
            Log.d("dodo", "폼목${result[productKey]!!.unit}의 갯수는 ${result[productKey]!!.number}")
            finalEcho += result[productKey]!!.unit * result[productKey]!!.number
            finalPrice += result[productKey]!!.price * result[productKey]!!.number
        }
        Log.d("dodo", "획득 메아리는 ${finalEcho}이고, 필요금액은 $finalPrice")


    }

    private fun findBiggestProduct(){
        biggestUnit = EchoProduct(0,0)
        for(echoProduct in originProducts.values){
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

    private fun calcRemainderMostEfficient(targetEcho: Int) {
        var minimalEfficiencyProduct = EchoProduct(0, 0)
        for(product in productsSortedByEfficiency) {
            minimalEfficiencyProduct = product
            if(product.unit <= targetEcho) {
                break
            }
        }

        var quotient: Int = (targetEcho / minimalEfficiencyProduct.unit.toFloat()).roundToInt()
        if(quotient == 0){
            quotient = 1
        }
        resultHM[minimalEfficiencyProduct.unit] = resultHM[minimalEfficiencyProduct.unit]!!.toInt() + quotient
        val calcResult = targetEcho - (minimalEfficiencyProduct.unit * quotient)
        Log.d("dodo", "calculating unit is ${minimalEfficiencyProduct.unit}, 몫 is ${quotient}, calcResult is $calcResult")
        if(calcResult > 0) {
            calcRemainderMostEfficient(calcResult)
        }
    }

    private fun calcRemainder(targetEcho: Int) {
        initMaximumByProduct(targetEcho)
        calcMain(targetEcho)
    }
    private fun initMaximumByProduct(targetEcho: Int) {
        for (product in productsSortedByUnit) {
            maximumByProduct.put(product.unit, (targetEcho/product.unit)+1)
        }
    }
    private fun calcMain(targetEcho: Int) {
        calcRecursion(targetEcho, targetEcho, 0, 0)
        val costs: ArrayList<Int> = ArrayList()
        for (result in results) {
            costs.add(calcCost(result))
        }
        countProduct(results[minIndex(costs)])
    }
    private fun calcRecursion(targetEcho: Int, minorTarget: Int, unitIndex: Int, qutientIndex: Int): ArrayList<EchoProduct> {
        val calcResult: ArrayList<EchoProduct> = ArrayList()
        val currentEchoProduct = originProducts.values.toList()[unitIndex]
        val minorTargetEcho = minorTarget - currentEchoProduct.unit
        currentEchoProduct.number += 1
        calcResult.add(currentEchoProduct)

        if (minorTargetEcho <= 0) {
            return calcResult
        }

        if (qutientIndex < maximumByProduct.values.toList()[unitIndex]) {
            calcResult.addAll(calcRecursion(targetEcho, minorTargetEcho,unitIndex, qutientIndex+1))

            if (calcGained(calcResult) >= targetEcho) {
                results.add(calcResult)
            }
        }
        if (unitIndex < maximumByProduct.size-1) {
            calcResult.addAll(calcRecursion(targetEcho, minorTargetEcho,unitIndex+1, 0))
        }

        return calcResult
    }
    private fun calcGained(products: ArrayList<EchoProduct>): Int {
        var result = 0
        for(product in products) {
            result += product.unit
        }
        return result
    }
    private fun calcCost(products: ArrayList<EchoProduct>): Int {
        var result = 0
        for(product in products) {
            result += product.price
        }
        return result
    }
    private fun minIndex(arg: ArrayList<Int>): Int {
        var resultIndex = 0
        val minValue = Int.MAX_VALUE

        for (index in 0 until arg.size) {
            if (minValue >= arg[index]) {
                resultIndex = index
            }
        }

        return resultIndex
    }
    private fun countProduct(products: ArrayList<EchoProduct>) {
        for (product in products) {
            result[product.unit]!!.number.plus(1)
        }
    }
}