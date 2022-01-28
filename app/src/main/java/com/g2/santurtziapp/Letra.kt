package com.g2.santurtziapp

class Letra(word: String) {

    var found = false
    var length = word.length
    var start = 0
    var end = 0

    //
    fun setLoc(tag: Int, isHorizontal: Boolean) {

        start = tag
        end = if(isHorizontal) tag + length - 1 else tag + (length -1)*10

    }//setLoc(tag: Int, isHorizontal: Boolean)

    //
    fun checkLoc(initTag: Int, finalTag: Int, isHorizontal: Boolean): Boolean{

        if(isHorizontal) {

            if(finalTag - initTag < length - 1) {

                return false

            }//if(finalTag - initTag < length - 1)

        }//if(isHorizontal)

        else {

            if((finalTag - initTag)/10 < length - 1) {

                return false

            }//if((finalTag - initTag)/10 < length - 1)

        }//if(!isHorizontal)

        if(start == initTag && end == finalTag) {

            return true

        }//if(start == initTag && end == finalTag)

        return false

    }//checkLoc(initTag: Int, finalTag: Int, isHorizontal: Boolean): Boolean

}///Letra(word: String)