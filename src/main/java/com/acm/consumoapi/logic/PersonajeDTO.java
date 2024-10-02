package com.acm.consumoapi.logic;


import com.google.gson.annotations.SerializedName;

public record PersonajeDTO(@SerializedName("id")int id,
                           @SerializedName("name")String name,
                           @SerializedName("status")String status,
                           @SerializedName("species")String species
                           ) {
}
