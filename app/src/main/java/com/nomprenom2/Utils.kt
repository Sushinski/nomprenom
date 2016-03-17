package com.nomprenom2

import android.view.InflateException

// that's rude...
fun checkedInflate( operation: () -> Unit)= try { operation(); true } catch (e: InflateException) { false }