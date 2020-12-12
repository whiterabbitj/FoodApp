package com.example.shoplist.GetData
import com.example.shoplist.Models.AddList
import com.example.shoplist.Models.ItemList
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class GetData   {

    fun makeRequest() : ArrayList<ItemList> {
        val okHttpClient = OkHttpClient()
        return parseResponse(okHttpClient.newCall(createRequest()).execute())

    }

    fun makeAddRequest():ArrayList<AddList>{
        val okHttpClient = OkHttpClient()
        return parseResponseAdd(okHttpClient.newCall(createRequestADD()).execute())
    }

    private fun createRequest(): Request {
        return Request.Builder()
            .url("https://whiraj.me/nodeapi/getShopItems")
            .build()
    }


    private fun createRequestADD(): Request {
        return Request.Builder()
            .url("https://whiraj.me/nodeapi/getAddItems")
            .build()
    }

    private fun parseResponse(response: Response): ArrayList<ItemList> {
        var productList  = arrayListOf<ItemList>()
        return if(response.isSuccessful){
            val body = response.body()?.string() ?: ""
            val json = jacksonObjectMapper()
            productList = json.readValue(body)
            productList
        } else{
            productList
        }
    }


    private fun parseResponseAdd(response: Response): ArrayList<AddList> {
        var addList  = arrayListOf<AddList>()
        return if(response.isSuccessful){
            val body = response.body()?.string() ?: ""
            val json = jacksonObjectMapper()
            addList = json.readValue(body)
            addList
        } else{
            addList
        }
    }
}