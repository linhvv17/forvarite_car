package com.dev.favoritecar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.billingclient.api.*
import com.dev.favoritecar.databinding.ActivityMainBinding
//import com.google.common.collect.ImmutableList



class InAppActivity : AppCompatActivity() {


//    private lateinit var binding: InAppActivityBinding
    private lateinit var billingClient: BillingClient
    private lateinit var productDetails: ProductDetails
    private lateinit var purchase: Purchase
    private val demo_product = "one_button_click"

    val TAG = "InAppPurchaseTag"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = InAppActivityBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        billingSetup()
    }


//    private fun billingSetup() {
//        billingClient = BillingClient.newBuilder(this)
//            .setListener(purchasesUpdatedListener)
//            .enablePendingPurchases()
//            .build()
//
//        billingClient.startConnection(object : BillingClientStateListener {
//            override fun onBillingSetupFinished(
//                billingResult: BillingResult
//            ) {
//                if (billingResult.responseCode ==
//                    BillingClient.BillingResponseCode.OK
//                ) {
//                    Log.i(TAG, "OnBillingSetupFinish connected")
//                    queryProduct(demo_product)
//                } else {
//                    Log.i(TAG, "OnBillingSetupFinish failed")
//                }
//            }
//
//            override fun onBillingServiceDisconnected() {
//                Log.i(TAG, "OnBillingSetupFinish connection lost")
//            }
//        })
//    }


}