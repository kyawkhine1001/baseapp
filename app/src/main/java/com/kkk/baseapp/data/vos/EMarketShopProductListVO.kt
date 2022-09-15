package com.kkk.baseapp.data.vos
import com.kkk.baseapp.network.networkresponse.emarket.EMarketShopProductListResponseItem
import java.io.Serializable

data class EMarketShopProductListVO(
    val imageUrl: String,
    val name: String,
    val price: Int,
    var quantity: Int,
    var isChecked:Boolean? = false
):Serializable{

    fun mappingFromNetworkResponse(response: EMarketShopProductListResponseItem):EMarketShopProductListVO{
        return EMarketShopProductListVO(
            response.imageUrl,
            response.name,
            response.price,
            1,
            false,
        )
    }
}