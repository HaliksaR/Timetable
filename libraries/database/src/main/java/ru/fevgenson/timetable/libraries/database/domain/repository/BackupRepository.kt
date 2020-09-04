package ru.fevgenson.timetable.libraries.database.domain.repository

interface BackupRepository {
    suspend fun createBackup()
    suspend fun restoreBackup(backupPath: String)
}