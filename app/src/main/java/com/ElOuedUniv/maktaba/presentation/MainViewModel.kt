package com.ElOuedUniv.maktaba.presentation

import androidx.lifecycle.ViewModel
import com.ElOuedUniv.maktaba.data.repository.OnboardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) : ViewModel() {
    val hasCompletedOnboarding: Flow<Boolean> = onboardingRepository.hasCompletedOnboarding
}
