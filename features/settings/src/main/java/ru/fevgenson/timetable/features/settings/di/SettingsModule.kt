package ru.fevgenson.timetable.features.settings.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.fevgenson.timetable.features.settings.presentation.SettingsViewModel
import ru.fevgenson.timetable.features.settings.presentation.notifications.SettingsNotificationsViewModel
import ru.fevgenson.timetable.features.settings.presentation.style.SettingsStyleViewModel

private val viewModelModule = module {
    viewModel { SettingsViewModel() }
    viewModel { SettingsStyleViewModel(get()) }
    viewModel { SettingsNotificationsViewModel(get()) }
}

val settingsModule = listOf(
    viewModelModule
)