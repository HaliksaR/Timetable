package ru.fevgenson.timetable.libraries.database.domain.usecase

import ru.fevgenson.timetable.libraries.database.domain.repository.BackupRepository

class RestoreBackupUseCase(
    private val repository: BackupRepository
) {

    suspend operator fun invoke(backupPath: String) {
        repository.restoreBackup(backupPath)
    }
}