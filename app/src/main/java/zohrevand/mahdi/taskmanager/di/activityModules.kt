package zohrevand.mahdi.taskmanager.di

import org.koin.android.experimental.dsl.viewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import zohrevand.mahdi.taskmanager.view.age.AgeViewModel
import zohrevand.mahdi.taskmanager.view.taskListpragmatic.TaskListVewModel

val mainModule = module {

   /* viewModel {
        TaskListVewModel()
        AgeViewModel()
    }*/

    viewModel<TaskListVewModel>()
    viewModel<AgeViewModel>()
}

