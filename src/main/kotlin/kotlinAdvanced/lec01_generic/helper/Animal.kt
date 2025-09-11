package org.example.kotlinAdvanced.lec01_generic.helper

abstract class Animal(
    val nmae: String
)

abstract class Fish(name: String) : Animal(name)

class GoldFish(name: String) : Fish(name)

class Carp(name: String) : Fish(name)