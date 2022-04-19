package com.example.kval

import android.content.Context
import androidx.room.*

@Entity // = таблица
data class DbModel(
    @ColumnInfo(name = "name") // = столбец
    var text: String? = null

) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}

@Dao // = штука, которая хранит функции к таблице
interface DbDao {

    @Query("SELECT * FROM DbModel") //= это означает запрос
    fun getData(): List<DbModel>

    @Insert // = добавить что-то в таблицу
    fun insert(list: List<DbModel>)
}

@Database(
    entities = [DbModel::class],
    version = 1 //если поменяли что-то в таблице, нужно поменять версию на любую другую, либо удалить
)
abstract class DbClass : RoomDatabase() {
    abstract fun dbDao(): DbDao

    companion object {
        fun build(context: Context) =
            Room.databaseBuilder(context, DbClass::class.java, "db").build().dbDao()
    }
}