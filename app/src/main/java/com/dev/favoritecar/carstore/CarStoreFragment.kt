package com.dev.favoritecar.carstore

import CarsAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.billingclient.api.*
import com.dev.favoritecar.R
import com.dev.favoritecar.databinding.CarStoreFragmentBinding
import com.google.common.collect.ImmutableList
import com.raywenderlich.android.monsters.base.BaseApplication


class CarStoreFragment : Fragment() {

    private lateinit var binding: CarStoreFragmentBinding
//    private val billingHelper by lazy {
//        (requireActivity().application as BaseApplication).appContainer.bill
//    }

    private var billingClient: BillingClient? = null


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

        //TODO: Connect ứng dụng của bạn với Google Billing

        //TODO: Connect ứng dụng của bạn với Google Billing
        billingClient?.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                //TODO: Sau khi connect thành công, thử lấy thông tin các sản phẩm

                Log.d("AAAA", "Connected")


                queryProducts()
            }

            override fun onBillingServiceDisconnected() {
                //TODO: Connect Google Play not success
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
            makePurchase(purchase.sku)
        }

        binding.carsList.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


    private fun makePurchase(skuId: String?) {
//        Log.d("AAAA", " sku is: " + sku.toString())

        //query infor product

        queryProducts()


        val queryProductDetailsParams =
            QueryProductDetailsParams.newBuilder()
                .setProductList(
                    ImmutableList.of(
                        skuId?.let {
                            QueryProductDetailsParams.Product.newBuilder()
                                .setProductId(it)
                                .setProductType(BillingClient.ProductType.INAPP)
                                .build()
                        }))
                .build()

        billingClient?.queryProductDetailsAsync(
            queryProductDetailsParams,
            ProductDetailsResponseListener { billingResult, productDetailsList ->
                // check billingResult
                // process returned productDetailsList
                skuTest = productDetailsList[0].toString()
                productDetails = productDetailsList[0]

                Log.d("skuDetails", skuTest.toString() + "\n")
                Log.d("skuDetails", "--------------")

            }
        )






        if (null != skuTest) {
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

        } else {
            Toast.makeText(
                requireContext(), getString(R.string.item_not_available), Toast
                    .LENGTH_SHORT
            ).show()
        }
    }


    private fun queryProducts() {
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


        val queryProductDetailsParams =
            QueryProductDetailsParams.newBuilder()
                .setProductList(
                    productList
                )
                .build()

        billingClient!!.queryProductDetailsAsync(
            queryProductDetailsParams,
            ProductDetailsResponseListener { billingResult, productDetailsList ->
                // check billingResult
                // process returned productDetailsList

                for (skuDetails in productDetailsList) {
                    Log.d("skuDetails", skuDetails.toString() + "\n")
                    Log.d("skuDetails", "--------------")
                    productDetails = productDetailsList[0]

                }

                skuTest = productDetailsList[0].toString()

            }
        )


    }


}