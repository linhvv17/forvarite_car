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
import com.raywenderlich.android.monsters.base.BaseApplication


class CarStoreFragment : Fragment() {

    private lateinit var binding: CarStoreFragmentBinding
    private val billingHelper by lazy {
        (requireActivity().application as BaseApplication).appContainer.bill
    }

    private var billingClient: BillingClient? = null

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
        onItemClickListener = {
//                purchase -> Toast.makeText(context, "Click buy", Toast.LENGTH_LONG).show()
                purchase ->
            makePurchase(purchase.sku)
        })

        binding.carsList.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


    fun getSkuPrice(sku: String?) : LiveData<String>? {
        if (null == sku) return null
        return billingHelper.getSkuPrice(sku).asLiveData()
    }

    fun makePurchase(sku: String?) {
        if (null != sku) {
            billingHelper.launchBillingFlow(requireActivity(), sku)
        } else {
            Toast.makeText(requireContext(), getString(R.string.item_not_available), Toast
                .LENGTH_SHORT).show()
        }
    }


    private fun queryProducts() {
        Log.d("AAAA", "queryProducts")
        // TODO: tạo list các product id (chính là product id bạn đã nhập ở bước trước) để lấy thông tin
        val productIds: MutableList<String> = ArrayList()
        productIds.add("pack1")
        val skuDetailsParams = SkuDetailsParams.newBuilder()
            .setSkusList(productIds)
            .setType(BillingClient.SkuType.INAPP) //TODO: Sử dụng INAPP với one-time product và SUBS với các gói subscriptions.
            .build()

        // TODO: Thực hiện query
        billingClient!!.querySkuDetailsAsync(
            skuDetailsParams
        ) { billingResult: BillingResult?, list: List<SkuDetails?>? ->
            Log.d("AAAA", "querySku")
            if (list != null) {
                Log.d("AAAA", "#null")
                for (skuDetails in list) {
                    Log.d("AAAA", skuDetails.toString())
                    Log.d("AAAA", "skuDetails")
                }
            }
        }
    }



}