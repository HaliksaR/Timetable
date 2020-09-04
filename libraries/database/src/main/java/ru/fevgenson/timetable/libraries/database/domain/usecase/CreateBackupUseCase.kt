package ru.fevgenson.timetable.libraries.database.domain.usecase

import ru.fevgenson.timetable.libraries.database.domain.repository.BackupRepository

class CreateBackupUseCase(
    private val backupRepository: BackupRepository
) {

    suspend operator fun invoke() {
        backupRepository.createBackup()
    }
}