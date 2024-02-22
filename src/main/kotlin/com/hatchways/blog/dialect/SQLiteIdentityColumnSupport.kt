package com.hatchways.blog.dialect

import org.hibernate.MappingException
import org.hibernate.dialect.identity.IdentityColumnSupportImpl

class SQLiteIdentityColumnSupport : IdentityColumnSupportImpl() {

    override fun supportsIdentityColumns(): Boolean {
        return true
    }

    override fun hasDataTypeInIdentityColumn(): Boolean {
        return false
    }

    override fun getIdentitySelectString(table: String, column: String, type: Int): String {
        return "select last_insert_rowid()"
    }

    override fun getIdentityColumnString(type: Int): String {
        return "integer"
    }
}
