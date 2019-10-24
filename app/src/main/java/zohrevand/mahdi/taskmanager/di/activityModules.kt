package zohrevand.mahdi.taskmanager.di

import org.koin.android.experimental.dsl.viewModel
import org.koin.dsl.module
import zohrevand.mahdi.taskmanager.view.taskList.TaskListVewModel

val mainModule = module {
    viewModel<TaskListVewModel>()
}

