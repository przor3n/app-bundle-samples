/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.samples.dynamicfeatures.state

import android.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalCoroutinesApi::class)
class ColorViewModel : ViewModel() {
    val backgroundColor = MutableStateFlow(Color.YELLOW)
    var ignoreFirstValue = true

    init {
        backgroundColor.onEach {
            if (ignoreFirstValue) {
                ignoreFirstValue = false
            } else {
                shouldLaunchReview = true
            }
        }.launchIn(viewModelScope)
    }

    var shouldLaunchReview: Boolean = false
        private set

    fun notifyReviewLaunchAttempted() {
        shouldLaunchReview = false
    }
}