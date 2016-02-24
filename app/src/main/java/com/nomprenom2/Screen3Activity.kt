package com.nomprenom2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.InflateException
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_screen3.*

class Screen3Activity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen3)

        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu) = checkedInflate { menuInflater.inflate(R.menu.menu_screen3, menu) }

    // показывает, какие подменю доступны в Options
    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId)
    {
        R.id.menu_favorites -> true
        R.id.menu_my_names -> true
        R.id.menu_choose_name -> true
        else -> super.onOptionsItemSelected(item)
    }
}

fun checkedInflate( operation: () -> Unit)= try { operation(); true } catch (e: InflateException) { false } // todo : move -> utils