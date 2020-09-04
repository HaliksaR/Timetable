package ru.fevgenson.timetable.libraries.database.data.repository

import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.fevgenson.timetable.libraries.database.domain.repository.BackupRepository
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

@Suppress("BlockingMethodInNonBlockingContext")
internal class BackupRepositoryImpl(
    private val localDatabase: RoomDatabase,
    private val localDatabasePath: String,
    private val defaultBackupPath: String
) : BackupRepository {

    override suspend fun createBackup() {
        withContext(Dispatchers.IO) {
            localDatabase.close()
            val localDataBaseFile = File(localDatabasePath)
            val backupFile = File(defaultBackupPath)
            backupFile.createNewFile()
            val localDatabaseFileInputStream = FileInputStream(localDataBaseFile)
            val backupFileOutputStream = FileOutputStream(backupFile)
            try {
                val buf = ByteArray(localDatabaseFileInputStream.available())
                localDatabaseFileInputStream.read(buf)
                backupFileOutputStream.write(buf)
            } finally {
                localDatabaseFileInputStream.close()
                backupFileOutputStream.close()
            }
        }
    }

    override suspend fun restoreBackup(backupPath: String) {
        withContext(Dispatchers.IO) {
            localDatabase.close()
            val localDataBaseFile = File(localDatabasePath)
            val backupFile = File(defaultBackupPath)
            backupFile.createNewFile()
            val localDatabaseFileOutputStream = FileOutputStream(localDataBaseFile)
            val backupFileInputStream = FileInputStream(backupFile)
            try {
                val buf = ByteArray(backupFileInputStream.available())
                backupFileInputStream.read(buf)
                localDatabaseFileOutputStream.write(buf)
            } finally {
                localDatabaseFileOutputStream.close()
                backupFileInputStream.close()
            }
        }
    }
}