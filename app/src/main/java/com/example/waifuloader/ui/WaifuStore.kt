package com.example.waifuloader.ui

import com.example.waifuloader.data.models.Waifu
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object WaifuStore {
    private val _waifuList = MutableStateFlow<List<Waifu>>(emptyList())
    val waifuList: StateFlow<List<Waifu>> = _waifuList.asStateFlow()

    private val _currentWaifu = MutableStateFlow(Waifu())
    val currentWaifu = _currentWaifu.asStateFlow()


    fun setCurrentWaifu(waifu: Waifu) {
        _currentWaifu.update { waifu }
    }

    /** Add a new waifu if not already in list */
    fun saveWaifu(newWaifu: Waifu) {
        _waifuList.update { list ->
            if (list.any { it.id == newWaifu.id }) list
            else list + newWaifu
        }
    }

    /** Remove a waifu by id */
    fun removeWaifu(id: String) {
        _waifuList.update { list ->
            list.filterNot { it.id == id }
        }
    }

    /** Get a specific waifu by id */
    fun getWaifuById(id: String): Waifu? {
        return _waifuList.value.find { it.id == id }
    }
}
