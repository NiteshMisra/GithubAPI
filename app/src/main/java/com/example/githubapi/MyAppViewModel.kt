package com.example.githubapi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapi.rest.APIClient
import com.example.githubapi.rest.response.GetGitRepoResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MyAppViewModel @Inject constructor(
    private val apiClient: APIClient
) : ViewModel() {

    private val _allRepo = MutableStateFlow<GetGitRepoResponse?>(null)
    val allRepo = _allRepo.asStateFlow()

    private val _progressLoading = MutableStateFlow<Boolean>(false)
    val progressLoading = _progressLoading.asStateFlow()

    /*
     Try catch can be made at global level as well
     */

    fun getALLRepo(){

        if (_allRepo.value != null) return
        _progressLoading.tryEmit(true)
        viewModelScope.launch {
            try {
                val response = apiClient.getTrendingRepo()
                if (response.isSuccessful){
                    _progressLoading.tryEmit(false)
                    _allRepo.tryEmit(response.body())
                }else{
                    _allRepo.tryEmit(null)
                    _progressLoading.tryEmit(false)
                }
            }catch (e : Exception){
                _allRepo.tryEmit(null)
                _progressLoading.tryEmit(false)
            }
        }
    }

}