package ru.fevgenson.timetable.features.timetable.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.koin.java.KoinJavaComponent.get
import ru.fevgenson.timetable.features.timetable.domain.usecase.GetLessonsUseCase
import ru.fevgenson.timetable.features.timetable.presentation.viewpager.PageDayViewModel
import ru.fevgenson.timetable.libraries.core.presentation.utils.eventutils.EventsDispatcher
import ru.fevgenson.timetable.libraries.core.utils.dateutils.DateUtils

class TimetableViewModel : ViewModel() {

    interface EventListener {
        fun navigateToCreate()
    }

    val selectedWeekLiveData = MutableLiveData(DateUtils.getCurrentWeek())
    val selectedDayLiveData = MutableLiveData(DateUtils.getCurrentDay())

    val eventsDispatcher = EventsDispatcher<EventListener>()

    val dayViewModelsList = List(DateUtils.WEEK_DAYS) {
        PageDayViewModel(
            currentWeekType = selectedWeekLiveData,
            currentDay = it,
            getLessonsUseCase = get(GetLessonsUseCase::class.java)
        )
    }

    fun onCreateLessonButtonClick() {
        eventsDispatcher.dispatchEvent { navigateToCreate() }
    }
}