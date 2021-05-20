package com.hamza.biblio

class Livre (
    var nom_p : String,
    var desc_p : String,
    var prix_p : Float
){

    override fun toString(): String {
        return "Livre(nom_p='$nom_p', desc_p='$desc_p', prix_p=$prix_p)"
    }
}