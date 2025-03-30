package com.example.trade_game.domain.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trade_game.data.PreferencesManager
import com.example.trade_game.domain.RetrofitInstance
import com.example.trade_game.domain.models.MarketTransactionRequest
import com.example.trade_game.domain.models.MarketTransactionResponse
import com.example.trade_game.domain.models.PriceHistoryResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MarketViewModel : ViewModel() {
    private val _marketSell = MutableStateFlow<MarketTransactionResponse?>(null)
    val marketSellData: StateFlow<MarketTransactionResponse?> = _marketSell.asStateFlow()

    private val _marketBuy = MutableStateFlow<MarketTransactionResponse?>(null)
    val marketBuyData: StateFlow<MarketTransactionResponse?> = _marketBuy.asStateFlow()

    private val _marketHistory = MutableStateFlow<PriceHistoryResponse?>(null)
    val marketHistory: StateFlow<PriceHistoryResponse?> = _marketHistory.asStateFlow()

    fun marketSell(amount: Float, assetId: Int, preferencesManager: PreferencesManager) {
        viewModelScope.launch {
            try {
                val token = preferencesManager.getUserData.first()?.get(3)
                val newTrans = MarketTransactionRequest(
                    amount = amount,
                    asset_id = assetId
                )
                val response = RetrofitInstance.apiService.marketSell(
                    marketTransaction = newTrans,
                    token = "Bearer $token"
                )
                _marketSell.value = response

            } catch (ex: Exception) {
                _marketSell.value = MarketTransactionResponse("Error", null, ex.localizedMessage)
            }
        }
    }

    fun marketBuy(amount: Float, assetId: Int, preferencesManager: PreferencesManager) {
        viewModelScope.launch {
            try {
                val token = preferencesManager.getUserData.first()?.get(3)
                val newTrans = MarketTransactionRequest(
                    amount = amount,
                    asset_id = assetId
                )
                val response = RetrofitInstance.apiService.marketBuy(
                    marketTransaction = newTrans,
                    token = "Bearer $token"
                )
                _marketSell.value = response

            } catch (ex: Exception) {
                _marketSell.value = MarketTransactionResponse("Error", null, ex.localizedMessage)
            }
        }
    }

    fun getPriceHistory(assetId: Int){
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.apiService.priceHistory(assetId)
                _marketHistory.value = response

            } catch (ex: Exception) {
                _marketHistory.value = PriceHistoryResponse("Error", emptyList(), ex.localizedMessage)
            }
        }
    }
}