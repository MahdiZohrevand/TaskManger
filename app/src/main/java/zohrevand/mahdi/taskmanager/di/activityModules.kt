package zohrevand.mahdi.taskmanager.di

import androidx.room.Room
import org.koin.android.experimental.dsl.viewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import zohrevand.mahdi.taskmanager.business.StopWatch
import zohrevand.mahdi.taskmanager.dataAccess.TaskManagerDatabase
import zohrevand.mahdi.taskmanager.dataAccess.preferences.UserAgePreference
import zohrevand.mahdi.taskmanager.dataAccess.preferences.UserAgePreferenceImp
import zohrevand.mahdi.taskmanager.view.age.AgeViewModel
import zohrevand.mahdi.taskmanager.view.newtask.NewTaskViewModel
import zohrevand.mahdi.taskmanager.view.taskList.TaskListVewModel

val mainModule = module {

    single {
        Room.inMemoryDatabaseBuilder(androidContext(), TaskManagerDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    viewModel {
        AgeViewModel(get())
    }

    viewModel {
        NewTaskViewModel(get())
    }

    viewModel {
        TaskListVewModel(get())
    }

    single<UserAgePreference> {
        UserAgePreferenceImp(androidContext())
    }

}




