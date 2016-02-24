package com.nomprenom2

import android.view.InflateException

/**
 * Created by android on 25.02.16.
 */
// that's rude...
fun checkedInflate( operation: () -> Unit)= try { operation(); true } catch (e: InflateException) { false }