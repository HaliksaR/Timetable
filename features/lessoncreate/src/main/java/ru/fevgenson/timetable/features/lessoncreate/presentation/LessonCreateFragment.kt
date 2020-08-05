package ru.fevgenson.timetable.features.lessoncreate.presentation

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.fevgenson.timetable.features.lessoncreate.R
import ru.fevgenson.timetable.features.lessoncreate.databinding.FragmentLessonCreateBinding
import ru.fevgenson.timetable.features.lessoncreate.presentation.viewpager.LessonCreateVPAdapter
import ru.fevgenson.timetable.libraries.core.presentation.utils.keyboardutils.closeKeyboard

class LessonCreateFragment : Fragment(), LessonCreateViewModel.EventListener {

    private lateinit var binding: FragmentLessonCreateBinding
    private val lessonCreateViewModel: LessonCreateViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initBinding(inflater, container)
        initViewPager()
        overrideSystemBackButton()
        initEventListener()

        return binding.root
    }

    private fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_lesson_create, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.lessonCreateViewModel = lessonCreateViewModel
    }

    private fun initViewPager() {
        val adapter = LessonCreateVPAdapter(lessonCreateViewModel)
        binding.viewPagerCreateLesson.offscreenPageLimit = adapter.itemCount
        binding.viewPagerCreateLesson.adapter = adapter
        binding.viewPagerCreateLesson.isUserInputEnabled = false
    }

    private fun overrideSystemBackButton() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            lessonCreateViewModel.onTopBackButtonClick()
        }
    }

    private fun initEventListener() {
        lessonCreateViewModel.eventsDispatcher.observe(viewLifecycleOwner, this)
    }

    override fun navigateToTimetable() {
        findNavController().popBackStack()
    }

    override fun closeKeyboard() {
        closeKeyboard(binding)
    }

    override fun setTimeAndInvokeTimePicker() {
        if (binding.lessonCreateViewModel?.isBegin!!) {
            invokeTimePickerDialog(
                binding.lessonCreateViewModel?.timeStartMin!!.div(60),
                binding.lessonCreateViewModel?.timeStartMin!!.rem(60)
            )
        } else {
            invokeTimePickerDialog(
                binding.lessonCreateViewModel?.timeEndMin!!.div(60),
                binding.lessonCreateViewModel?.timeEndMin!!.rem(60)
            )
        }
    }

    private fun invokeTimePickerDialog(oursOnStart: Int, minOnStart: Int) {
        val listener = TimePickerDialog.OnTimeSetListener { _: TimePicker, ours: Int, min: Int ->
            binding.lessonCreateViewModel?.onDoneTimePickerSetTime(ours, min)
        }

        TimePickerDialog(requireContext(), listener, oursOnStart, minOnStart, true).show()
    }
}