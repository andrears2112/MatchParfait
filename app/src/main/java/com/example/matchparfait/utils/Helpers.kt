package com.example.matchparfait.utils

import com.example.matchparfait.model.entitys.Product
import com.example.matchparfait.model.entitys.User

class Helpers {
    companion object Shared {

        private lateinit var actualUser : User
        private lateinit var token: String
        private lateinit var products : List<Product>
        private lateinit var selectProduct: Product

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
            /*val productList = listOf(
                Product(
                    productId = "1",
                    productName = "Shampoo Anticaspa",
                    productBrand = "Head & Shoulders",
                    price = 50,
                    quantity = 10,
                    stars = 4,
                    photo = "https://example.com/shampoo.jpg",
                    description = "Shampoo anticaspa para todo tipo de cabello",
                    ingredients = "Aqua, Sodium Laureth Sulfate, Zinc Carbonate",
                    colors = "Blanco",
                    photos = "https://example.com/shampoo1.jpg, https://example.com/shampoo2.jpg",
                    type = "Cuidado del Cabello",
                    notes = "Usar diariamente para mejores resultados",
                    addition_date = "2023-01-15",
                    classification = "Higiene"
                ),
                Product(
                    productId = "2",
                    productName = "Crema Hidratante",
                    productBrand = "Nivea",
                    price = 30,
                    quantity = 20,
                    stars = 5,
                    photo = "https://example.com/crema.jpg",
                    description = "Crema hidratante para piel seca",
                    ingredients = "Aqua, Glycerin, Paraffinum Liquidum",
                    colors = "Blanco",
                    photos = "https://example.com/crema1.jpg, https://example.com/crema2.jpg",
                    type = "Cuidado de la Piel",
                    notes = "Aplicar por las mañanas y noches",
                    addition_date = "2023-02-10",
                    classification = "Higiene"
                ),
                Product(
                    productId = "3",
                    productName = "Pasta de Dientes",
                    productBrand = "Colgate",
                    price = 25,
                    quantity = 15,
                    stars = 4,
                    photo = "https://lunaricode.com/assets/images/RBBL00001_1.png",
                    description = "Pasta de dientes con flúor para protección contra caries",
                    ingredients = "Sodium Fluoride, Aqua, Sorbitol",
                    colors = "Blanco",
                    photos = "https://example.com/pasta1.jpg, https://example.com/pasta2.jpg",
                    type = "Cuidado Dental",
                    notes = "Cepillar al menos dos veces al día",
                    addition_date = "2023-03-05",
                    classification = "0"
                )
            )*/
            return this.products
        }

        fun saveSelectedProduct(prod : Product){
            this.selectProduct = prod
        }

        fun getSelectedProduct() : Product{
            return this.selectProduct
        }
    }
}