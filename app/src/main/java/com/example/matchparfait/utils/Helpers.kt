package com.example.matchparfait.utils

import com.example.matchparfait.model.entitys.AddressUser
import com.example.matchparfait.model.entitys.Card
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
        private lateinit var card : Card
        private lateinit var userRegister : User
        private lateinit var sourceEdit : String

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

        fun saveCard(card : Card){
            this.card = card
        }

        fun getCard() : Card{
            return this.card
        }

        fun saveUserToRegister(user : User){
            this.userRegister = user
        }

        fun getUserToRegister() : User {
            return this.userRegister
        }

        fun setSourceEdit(source : String){
            this.sourceEdit = source
        }

        fun getSourceEdit() : String{
            return this.sourceEdit
        }

        fun destroySession(){
            this.actualUser = User()
        }
    }
}