package zohrevand.mahdi.taskmanager.di

import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import zohrevand.mahdi.taskmanager.dataAccess.TaskManagerDatabase
import zohrevand.mahdi.taskmanager.dataAccess.preferences.UserAgePreference
import zohrevand.mahdi.taskmanager.dataAccess.preferences.UserAgePreferenceImp
import zohrevand.mahdi.taskmanager.view.age.AgeViewModel
import zohrevand.mahdi.taskmanager.view.datepicker.DatePickerViewModel
import zohrevand.mahdi.taskmanager.view.newtask.NewTaskViewModel
import zohrevand.mahdi.taskmanager.view.taskList.TaskListVewModel

val mainModule = module {

    single {
       /* Room.databaseBuilder(androidContext(), TaskManagerDatabase::class.java , "task_manger")
            .allowMainThreadQueries()
            .build()*/

        TaskManagerDatabase.getInstance(androidContext())
    }

    viewModel {
        AgeViewModel(get())
    }

    viewModel {
        NewTaskViewModel(get(), androidContext())
    }

    viewModel {
        TaskListVewModel(get())
    }

    viewModel {
        DatePickerViewModel()
    }

    single<UserAgePreference> {
        UserAgePreferenceImp(androidContext())
    }

}




