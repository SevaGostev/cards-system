package com.gmail.vmgostev.cardssystem.auth.rest;

public record RegisterRequest(String name, String email, String pw, String confirmpw) {

}
