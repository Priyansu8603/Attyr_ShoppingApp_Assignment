package com.example.attyr_shoppingapp_assignment.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.attyr_shoppingapp_assignment.Model.CategoryModel
import com.example.attyr_shoppingapp_assignment.Model.ItemsModel
import com.example.attyr_shoppingapp_assignment.Model.SliderModel
import com.example.attyr_shoppingapp_assignment.Repository.MainRepository

class MainViewModel():ViewModel() {
    private val repository = MainRepository()


    fun loadBanner(): LiveData<MutableList<SliderModel>>{
        return repository.loadBanner()
    }


    fun loadCategory(): LiveData<MutableList<CategoryModel>>{
        return repository.loadCategory()
    }

    fun loadPopular(): LiveData<MutableList<ItemsModel>>{
        return repository.loadPopular()
    }

    fun loadFiltered(id: String): LiveData<MutableList<ItemsModel>>{
        return repository.loadFiltered(id)
    }

}