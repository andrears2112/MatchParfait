package com.example.matchparfait.utils

import com.example.matchparfait.model.entitys.AddressUser
import com.example.matchparfait.model.entitys.Product
import com.example.matchparfait.model.entitys.User

class Helpers {
    companion object Shared {

        private lateinit var actualUser : User
        private lateinit var token: String
        private lateinit var products : List<Product>
        private lateinit var selectProduct: Product
        private lateinit var total : String
        private lateinit var editAddress : AddressUser

        fun startSession(token : String, user : User){
            this.actualUser = user
            this.token = token
        }

        fun getUser() : User {
            return this.actualUser
        }

        fun getToken() : String {
            return this.token
        }

        fun saveProducts(list : List<Product>){
            this.products = list
        }

        fun getProducts() : List<Product>{
            return this.products
        }

        fun saveSelectedProduct(prod : Product){
            this.selectProduct = prod
        }

        fun getSelectedProduct() : Product{
            return this.selectProduct
        }

        fun saveTotal(amount : String){
            this.total = amount
        }

        fun getTotal() : String{
            return this.total
        }

        fun saveAddressToEdit(address : AddressUser){
            this.editAddress = address
        }

        fun getAddressToEdit() : AddressUser{
            return this.editAddress
        }
    }
}