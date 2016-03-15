package com.nomprenom2.model

import io.realm.RealmObject
import io.realm.annotations.Ignore

/**
 * Created by musachev on 01.03.2016.
 */
class Name(var name: String? = "unknown") : RealmObject()
{
    var nickname = name
    var group_id: Int? = null
    @Ignore
    var tempRef : Name? = null
}
