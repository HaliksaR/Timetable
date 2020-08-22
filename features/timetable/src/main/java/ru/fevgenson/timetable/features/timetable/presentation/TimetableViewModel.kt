package ru.fevgenson.timetable.features.timetable.presentation

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.get
import ru.fevgenson.libraries.navigation.di.NavigationConstants
import ru.fevgenson.timetable.features.timetable.presentation.viewpager.PageDayViewModel
import ru.fevgenson.timetable.libraries.core.presentation.utils.eventutils.EventsDispatcher
import ru.fevgenson.timetable.libraries.core.utils.dateutils.DateUtils

class TimetableViewModel : ViewModel() {

    interface EventListener {
        fun navigateToCreate(bundle: Bundle)
        fun showDeleteDialog(id: Long)
    }

    val selectedWeekLiveData = MutableLiveData(DateUtils.getCurrentWeek())
    val selectedDayLiveData = MutableLiveData(DateUtils.getCurrentDay())

    val eventsDispatcher = EventsDispatcher<EventListener>()

    val dayViewModelsList = List(DateUtils.WEEK_DAYS) {
        get(PageDayViewModel::class.java) {
            parametersOf(it, this)
        }
    }

    fun onCreateLessonButtonClick() {
        eventsDispatcher.dispatchEvent {
            with(NavigationConstants.LessonCreate) {
                navigateToCreate(
                    Bundle().apply {
                        putInt(
                            DAY,
                            requireNotNull(selectedDayLiveData.value) { "day can't be null" }
                        )
                        putInt(
                            WEEK_TYPE,
                            requireNotNull(selectedWeekLiveData.value) { "week can't be null" }
                        )
                        putInt(OPEN_TYPE, CREATE)
                    }
                )
            }
        }
    }

    fun onEditLessonMenuClick(id: Long) {
        eventsDispatcher.dispatchEvent {
            with(NavigationConstants.LessonCreate) {
                navigateToCreate(
                    Bundle().apply {
                        putLong(LESSON_ID, id)
                        putInt(OPEN_TYPE, EDIT)
                    }
                )
            }
        }
    }

    fun onCopyLessonMenuClick(id: Long) {
        eventsDispatcher.dispatchEvent {
            with(NavigationConstants.LessonCreate) {
                navigateToCreate(
                    Bundle().apply {
                        putLong(LESSON_ID, id)
                        putInt(OPEN_TYPE, COPY)
                    }
                )
            }
        }
    }

    fun onDeleteLessonMenuClick(id: Long) {
        eventsDispatcher.dispatchEvent { showDeleteDialog(id) }
    }

    fun onDeleteDialogOkButtonClick(id: Long) {
        Log.d("menu", "delete $id")
    }
}