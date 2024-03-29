package com.dev.favoritecar.carstore

import CarsAdapter
import android.app.AlertDialog
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.billingclient.api.*
import com.dev.favoritecar.R
import com.dev.favoritecar.databinding.CarStoreFragmentBinding
import com.google.common.collect.ImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext


class CarStoreFragment(override val coroutineContext: CoroutineContext) : Fragment(), PurchasesUpdatedListener, CoroutineScope {

    private lateinit var binding: CarStoreFragmentBinding
//    private val billingHelper by lazy {
//        (requireActivity().application as BaseApplication).appContainer.bill
//    }

    private var billingClient: BillingClient? = null
//    private var billingClient: BillingClient? = billingHelper.



    private lateinit var listSku: List<SkuDetails?>

    private var skuTest = ""

    private var productDetails: ProductDetails? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CarStoreFragmentBinding.inflate(layoutInflater)
        billingClient = context?.let {
            BillingClient.newBuilder(it)
                .enablePendingPurchases()
                .setListener { billingResult: BillingResult?, list: List<Purchase?>? -> }
                .build()
        }
        billingClient?.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                Log.d("AAAA", "Connected")

            }

            override fun onBillingServiceDisconnected() {
            }

        })

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CarsAdapter(
            this,
        ) {
                purchase -> {}
            queryProducts(purchase.position)

        }

        binding.carsList.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }



    private fun queryProducts(position : Int) {
        val productList: MutableList<QueryProductDetailsParams.Product> = ArrayList()

        productList.add(
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId("pack1")
                .setProductType(BillingClient.ProductType.INAPP)
                .build()
        )

        productList.add(
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId("pack2")
                .setProductType(BillingClient.ProductType.INAPP)
                .build()
        )

        productList.add(
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId("pack3")
                .setProductType(BillingClient.ProductType.INAPP)
                .build()
        )

        productList.add(
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId("pack4")
                .setProductType(BillingClient.ProductType.INAPP)
                .build()
        )

        productList.add(
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId("pack5")
                .setProductType(BillingClient.ProductType.INAPP)
                .build()
        )

        productList.add(
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId("pack6")
                .setProductType(BillingClient.ProductType.INAPP)
                .build()
        )


        val queryProductDetailsParams =
            QueryProductDetailsParams.newBuilder()
                .setProductList(
                    productList
                )
                .build()

        billingClient!!.queryProductDetailsAsync(
            queryProductDetailsParams,
            ProductDetailsResponseListener { billingResult1, productDetailsList ->
                productDetails = productDetailsList[position]
                skuTest = productDetailsList[position].toString()
                Log.d("skuTest", skuTest)

                val billingFlowParams = BillingFlowParams.newBuilder()
                    .setProductDetailsParamsList(
                        ImmutableList.of(
                            productDetails?.let {
                                BillingFlowParams.ProductDetailsParams.newBuilder()
                                    .setProductDetails(it)
                                    .build()
                            }
                        )
                    )
                    .build()
                val billingResult = billingClient?.launchBillingFlow(requireActivity(), billingFlowParams)

            }
        )



    }

    override fun onPurchasesUpdated(billingResult: BillingResult, purchases: List<Purchase>?) {
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (purchase in purchases) {
                val consumeParams =
                    ConsumeParams.newBuilder()
                        .setPurchaseToken(purchase.purchaseToken)
                        .build()


                val listener = ConsumeResponseListener { billingResult, purchaseToken ->
                        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                            val inflater = this.requireActivity().getSystemService(
                                LAYOUT_INFLATER_SERVICE
                            ) as LayoutInflater
                            val v: View = inflater.inflate(R.layout.thank_popup, null)
                            AlertDialog.Builder(this.requireActivity())
                                .setIcon(R.mipmap.ic_launcher)
                                .setView(v)
                                .setTitle("Thank you")
                                .setMessage("Have a nice day!")
                                .show()
                        }
                    }
                billingClient!!.consumeAsync(consumeParams, listener)

            }
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            // Handle an error caused by a user cancelling the purchase flow.
        } else {
            // Handle any other error codes.
        }

    }



    suspend fun handlePurchase(pur: Purchase) {
        // Purchase retrieved from BillingClient#queryPurchasesAsync or your PurchasesUpdatedListener.
        val purchase : Purchase = pur

        // Verify the purchase.
        // Ensure entitlement was not already granted for this purchaseToken.
        // Grant entitlement to the user.

        val consumeParams =
            ConsumeParams.newBuilder()
                .setPurchaseToken(purchase.getPurchaseToken())
                .build()
        val consumeResult = withContext(Dispatchers.IO) {
            billingClient!!.consumePurchase(consumeParams)
        }
    }


}


