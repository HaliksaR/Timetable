package ru.fevgenson.timetable.features.lessoncreate.presentation.viewpager

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.fevgenson.timetable.features.lessoncreate.presentation.LessonCreateViewModel

class LessonCreateVPAdapter(private val viewModel: LessonCreateViewModel) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int = position

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            LessonCreateViewModel.MAIN_PAGE -> VHMainPage.from(parent, viewModel)
            LessonCreateViewModel.LOCATION_AND_TYPE_PAGE -> VHLocalAndTypePage.from(
                parent,
                viewModel
            )
            LessonCreateViewModel.TEACHER_PAGE -> VHTeacherPage.from(parent, viewModel)
            else -> throw ClassCastException("Unknown ViewHolder type $viewType")
        }

    override fun getItemCount() = LessonCreateViewModel.PAGES_COUNT

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }
}