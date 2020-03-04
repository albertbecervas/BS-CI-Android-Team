package com.diet.dietfyer.di.component

import com.diet.dietfyer.di.module.NavigationModule
import com.diet.dietfyer.di.module.data.RepositoryModule
import com.diet.dietfyer.di.module.domain.InteractorModule
import com.diet.dietfyer.di.module.presentation.PresenterModule
import com.diet.dietfyer.di.module.presentation.RouterModule
import com.diet.dietfyer.di.module.presentation.ViewModule
import dagger.Component

@Component(
    modules = [ViewModule::class, PresenterModule::class, RouterModule::class,
        NavigationModule::class, InteractorModule::class, RepositoryModule::class]
)
interface TestViewComponent : ViewComponent {
}