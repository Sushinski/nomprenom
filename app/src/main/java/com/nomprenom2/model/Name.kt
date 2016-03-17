package com.nomprenom2.model

import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey

/**
 * Created by musachev on 01.03.2016.
 */
/* Your model has to extend RealmObject.
   Class and all of the properties must be annotated with 'open' (as classes and functions are final by default).
   You can put properties in the constructor as long as all of them are initialized with
   default values. This ensures that an empty constructor is generated.
   All properties are persisted by default. Properties can be annotated with PrimaryKey or Index.
*/
open class Name(@PrimaryKey open var name: String? = "unknown") : RealmObject()
{
    open var nickname = name
    open var group_id: Int? = null
    @Ignore
    open var tempRef : Name? = null
    open var id: Long = 0
}
