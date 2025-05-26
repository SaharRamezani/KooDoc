package com.example.kidzi.di.db

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val LEVEL = "level"
private const val PARENT = "parent"
private const val PARENT_NAME = "parent_name"
private const val PARENT_JOB = "parent_job"
private const val PARENT_BIRTH = "parent_birth"
private const val PARENT_CARE = "parent_care"
private const val CURRENT_KID = "ck"
private const val CAN_OPEN = "co"
private const val AGE_UNIT_KEY = "age_unit"
private val SELECTED_MILKS = "selected_milks"

class PreferenceManager @Inject constructor(@ApplicationContext context: Context) {
    private val sharedPreferences = context.getSharedPreferences("MyPref",Context.MODE_PRIVATE)

    fun canOpen(): Boolean{return sharedPreferences.getBoolean(CAN_OPEN,true)}
    fun updateOpen(open: Boolean){
        Log.i("Log1","can open updateing is : $open")
        sharedPreferences.edit().putBoolean(CAN_OPEN,open).commit()
    }

    fun getLastAgeUnit(): String =
        sharedPreferences.getString(AGE_UNIT_KEY, "ماه")!!

    /** Saves the chosen age unit */
    fun updateLastAgeUnit(unit: String) {
        sharedPreferences.edit()
            .putString(AGE_UNIT_KEY, unit)
            .apply()
    }

    /** Save selected milk english names */
    fun saveSelectedMilks(selected: Set<String>) {
        sharedPreferences.edit()
            .putStringSet(SELECTED_MILKS, selected)
            .apply()
    }

    /** Retrieve selected milk english names */
    fun getSelectedMilks(): Set<String> {
        return sharedPreferences.getStringSet(SELECTED_MILKS, emptySet()) ?: emptySet()
    }

    fun getCurrentKid(): Int{return sharedPreferences.getInt(CURRENT_KID,0)}
    fun updateCurrentKid(kid: Int){ sharedPreferences.edit().putInt(CURRENT_KID,kid).commit() }

    fun getLevel(): Int{return sharedPreferences.getInt(LEVEL,0) }
    fun updateLevel(level: Int){ sharedPreferences.edit().putInt(LEVEL,level).commit() }

    fun updateParent(parent: Int){ sharedPreferences.edit().putInt(PARENT,parent).commit() }
    fun getParent():Int{ return sharedPreferences.getInt(PARENT,0) }

    fun updateParentName(name: String){ sharedPreferences.edit().putString(PARENT_NAME,name).commit() }
    fun getParentName(): String{ return sharedPreferences.getString(PARENT_NAME,"")!! }

    fun updateParentJob(job: Int){ sharedPreferences.edit().putInt(PARENT_JOB,job).commit() }
    fun getParentJob(): Int{return sharedPreferences.getInt(PARENT_JOB,0)}

    fun updateParentBirth(birth: String){ sharedPreferences.edit().putString(PARENT_BIRTH,birth).commit() }
    fun getParentBirth(): String{ return sharedPreferences.getString(PARENT_BIRTH,"")!! }

    fun updateParentCare(care: Int){ sharedPreferences.edit().putInt(PARENT_CARE,care).commit() }
    fun getParentCare(): Int{return sharedPreferences.getInt(PARENT_CARE,0)}

    fun setParentName(name: String) {
        sharedPreferences.edit().putString("parent_name", name).apply()
    }

    fun setParentCare(value: Int) {
        sharedPreferences.edit().putInt("parent_care", value).apply()
    }

    fun setParentJob(value: Int) {
        sharedPreferences.edit().putInt("parent_job", value).apply()
    }

    fun setParentBirth(date: String) {
        sharedPreferences.edit().putString("parent_birth", date).apply()
    }
}