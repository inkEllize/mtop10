package com.hackeru.reversetask

import android.util.Base64
import java.security.MessageDigest

val CRT: String = "8D:C2:8B:F3:FA:A7:32:50:40:A7:76:D5:FF:0F:6F:03:04:11:60:C3:98:64:0E:B9:00:2F:03:55:0D:A1:6A:94"

///gOVLGXLgx2BOPYdS6sJ77tWhFVJVn3WKQlUAyw3aUY=
val HASSDDAD = "/gOVLGXLgx2BOPYdS6sJ77tWhFVJVn3WKQlUAyw3aUY="
fun hashString(input: String): String {
    val bytes = MessageDigest
            .getInstance("SHA-256")
            .digest(input.toByteArray())
    return Base64.encodeToString(bytes, Base64.DEFAULT)
}

