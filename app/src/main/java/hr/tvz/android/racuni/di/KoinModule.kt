package hr.tvz.android.racuni.di

import androidx.room.Room
import hr.tvz.android.racuni.data.MainViewModel
import hr.tvz.android.racuni.data.Repository
import hr.tvz.android.racuni.data.Baza
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val roomModule = module {
    single { Room.databaseBuilder(get(), Baza::class.java, "bazaPodataka")
        .fallbackToDestructiveMigration()
        .build()}
    single { get<Baza>().dao() }
}

val repositoryModule = module {
    single { Repository(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}