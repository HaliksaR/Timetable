package ru.fevgenson.timetable.features.dictionary.presentation

import androidx.lifecycle.ViewModel
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.get
import ru.fevgenson.timetable.features.dictionary.domain.Categories
import ru.fevgenson.timetable.features.dictionary.presentation.viewpager.PageCategoryViewModel
import ru.fevgenson.timetable.libraries.core.presentation.utils.eventutils.EventsDispatcher

class DictionaryViewModel : ViewModel() {

    interface EventListener {

        fun goToListOfLessonsByCategoryFragment(categoryItem: String)
    }

    val eventsDispatcher = EventsDispatcher<EventListener>()
    val listOfPageCategoryViewModel = List(Categories.TOTAL_CATEGORIES) { categoryType ->
        get(PageCategoryViewModel::class.java) {
            parametersOf(categoryType, this)
        }
    }

    fun onCategoryItemClick(categoryItem: String) {
        eventsDispatcher.dispatchEvent { goToListOfLessonsByCategoryFragment(categoryItem) }
    }
}