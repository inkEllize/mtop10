package com.csr1.forreverse

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {
    public var valueLiveData:MutableLiveData<Int> = MutableLiveData()
    var dummyDataLive:MutableLiveData<Int> = MutableLiveData()
    init {
        valueLiveData.value = 1
        dummyDataLive.value = 1
    }

}