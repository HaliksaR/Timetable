package ru.fevgenson.timetable.features.timetable.presentation.viewpager

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.fevgenson.timetable.features.timetable.domain.entities.TimetableLesson
import ru.fevgenson.timetable.features.timetable.domain.usecase.GetLessonsUseCase
import ru.fevgenson.timetable.features.timetable.presentation.TimetableViewModel
import ru.fevgenson.timetable.libraries.core.utils.dateutils.DateUtils

class PageDayViewModel(
    private val parentViewModel: TimetableViewModel,
    currentDay: Int,
    getLessonsUseCase: GetLessonsUseCase
) : ViewModel() {

    private val firstWeekLessons = getLessonsUseCase(
        weekType = DateUtils.FIRST_WEEK,
        day = currentDay
    )

    private val secondWeekLessons = getLessonsUseCase(
        weekType = DateUtils.SECOND_WEEK,
        day = currentDay
    )

    val lessons = MediatorLiveData<List<TimetableLesson>>().apply {
        addSource(firstWeekLessons) {
            if (parentViewModel.selectedWeekLiveData.value == DateUtils.FIRST_WEEK) {
                value = it
            }
        }
        addSource(secondWeekLessons) {
            if (parentViewModel.selectedWeekLiveData.value == DateUtils.SECOND_WEEK) {
                value = it
            }
        }
        addSource(parentViewModel.selectedWeekLiveData) {
            value = when (it) {
                DateUtils.FIRST_WEEK -> firstWeekLessons.value
                DateUtils.SECOND_WEEK -> secondWeekLessons.value
                else -> throw IllegalArgumentException()
            }
        }
    }

    val uiState = Transformations.map(lessons) {
        when {
            it == null -> PageDayUIState.Loading
            it.isEmpty() -> PageDayUIState.Empty
            else -> PageDayUIState.Content
        }
    }

    fun copyLesson(lessonId: Long) {
        parentViewModel.onCopyLessonMenuClick(lessonId)
    }

    fun deleteLesson(lessonId: Long) {
        parentViewModel.onDeleteLessonMenuClick(lessonId)
    }

    fun editLesson(lessonId: Long) {
        parentViewModel.onEditLessonMenuClick(lessonId)
    }
}