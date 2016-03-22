package com.nomprenom2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.nomprenom2.model.Name
import io.realm.Realm

import kotlinx.android.synthetic.main.activity_screen3.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class Screen3Activity : AppCompatActivity(), AnkoLogger
{
    private fun complexAutoCloseQuery(realm: Realm) = StringBuilder("\n\nPerforming complex Query operation...").apply {
        realm.use { realm -> // todo: this one uses autocloseable syntax, so we don't need to close realm in override fun onDestroy() on exit!!!
            //val results = realm.where(Person::class.java)
            //.between("age", 7, 9)
            //.beginsWith("name", "Person")
            val results = realm.where(Name::class.java).findAll()
            append("\nSize of result set: ").append(results.size)
        }
    }.toString()

    private fun addTenName(realm: Realm) {

        val receiver = StringBuilder()
        with(receiver)
        {
            for (group_id in 0..9) {
                val new_name = realm.createObject(Name::class.java).apply { // todo: add name function
                    name = "Name no : $group_id"
                    nickname = "Kinda duda $name"
                    group_id
                    // The field 'tempReference' is annotated with @Ignore.
                    // This means it is not saved as part of the RealmObject.
                    tempRef = Name("god object")
                }
                append("\nAnd the new name is: ").append(new_name)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen3)
        setSupportActionBar(toolbar)

        info("Screen3 starting")

        /*
        realm = Realm.getInstance(this)
        realm.beginTransaction()
            val new_name = realm.createObject(Name::class.java)
            println(new_name)
        realm.commitTransaction()

        realm.beginTransaction()
        for (group_id in 0..9) {
            val new_name = realm.createObject(Name::class.java).apply { // todo: add name function
                name = "Name no : $group_id"
                nickname = "Kinda duda $name"
                group_id
                // The field 'tempReference' is annotated with @Ignore.
                // This means it is not saved as part of the RealmObject.
                tempRef = Name("god object")
            }
            println(new_name)
        }
        realm.commitTransaction()

        with (realm) {
            // These operations are small enough that we can safely run them on the UI thread
            addTenName(realm)
        }

        complexAutoCloseQuery(realm)

        // More complex operations can be executed on another thread
        async() {
            fun realmInstance() = Realm.getInstance(realmConfig)
            var info = complexReadWrite(realmInstance()) + complexQuery(realmInstance())

            uiThread { act ->
                act.log(info)
            }
        }
        */
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

    private lateinit var realm: Realm
}