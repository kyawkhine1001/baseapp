package com.kkk.baseapp.network.networkresponse.emarket

import com.kkk.baseapp.data.vos.EMarketShopProductListVO
import java.io.Serializable

data class EMarketShopProductListResponseItem(
    val imageUrl: String,
    val name: String,
    val price: Int,
):Serializable{

    fun mappingToVO(): EMarketShopProductListVO {
        return EMarketShopProductListVO(
            this.imageUrl,
            this.name,
            this.price,
            1,
            false,
        )
    }
}