package ru.fevgenson.timetable.features.lessoncreate.domain.usecase

import ru.fevgenson.timetable.libraries.database.domain.entities.Lesson
import ru.fevgenson.timetable.libraries.database.domain.repository.LessonRepository

class SaveLessonsUseCase(private val repository: LessonRepository) {

    suspend operator fun invoke(lessons: List<Lesson>) {
        lessons.forEach { repository.saveLesson(it) }
    }
}