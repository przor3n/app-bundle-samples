# Play Core sample
This is an API sample to showcase the following parts of the Play Core library in Kotlin:
* On-demand Play Feature Delivery
* In-app Updates
* In-app Reviews

# Overview
## Functionality
The sample app is a "flashlight" app that lets you light up the screen with a yellow background.
You can also pick a color for the flashlight from a photo, by using the camera.

## Implementation
The app is designed to show how to use the respective Play Core APIs using by using the Play Core KTX artifact, which provides coroutine (`suspend` and `Flow`) versions of all Play Core APIs.

Important parts of the code to look at are:
* `MainFragment` - contains the main screen of the application with the flashlight on/off button, and observes states and events coming from the various ViewModels described below to handle the UI for on-demand feature installation, in-app updates and in-app reviews
* `InstallViewModel` - handles installation and exposes installation state of on-demand feature modules. Currently the app has one such feature module: `features/picture`, which is needed for extracting a color from a photo
* `UpdateViewModel` - exposes the availability and state of an in-app update
* `ReviewViewModel` - takes care of pre-warming the review request, which is needed to show the UI review quickly when requested. This ViewModel is scoped to the Activity and is shared between both Fragments in order to start pre-warming the request when a color is chosen in `PaletteFragment`
* `PaletteFragment` - this fragment is in an on-demand dynamic feature module (`features/picure`) and takes care of requesting the camera to take a photo and extracting the colors from it. The color is stored in the `ColorViewModel` which is scoped to the Activity and is shared between both Fragments

## Usage
To test the sample locally, you can use the following Gradle task:
```
./gradlew debugInstallApksForLocalTesting
```

This will use `bundletool` to build an APK set in local testing mode, and install the base app to a
connected device or emulator, while also pushing all split APKs into the device's storage.

At runtime, based on the local testing meta-data set in the Manifest by `bundletool`,
`SplitInstallManager` will load dynamic feature modules from local storage instead of connecting to
the Play Store.

If you wish to test real behavior, including in-app updates and in-app reviews, you can use
[internal app sharing](https://support.google.com/googleplay/android-developer/answer/9303479?hl=en) on the Play Store.

### <variant>InstallApksForLocalTesting task
The task described above comes from a custom Gradle plugin that can be found in the `buildSrc` directory. It uses the new [Android Gradle plugin Variant API](https://medium.com/androiddevelopers/new-apis-in-the-android-gradle-plugin-f5325742e614) to read the final location of the produced Android App Bundle file, then extracts APKs from it and installs them on the device.

While not the focus of this sample, if you're interested in learning more about custom Gradle tasks and using the Variant API, you're encouraged to look at the code or copy it to your project, however please note that this plugin is not production-ready and will not be supported.

You can also check out the [Android Gradle recipes](https://github.com/android/gradle-recipes) repository to see more examples of the Variant API in action.

# License
-------
```
Copyright 2020 The Android Open Source Project, Inc.

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements.  See the NOTICE file distributed with this work for
additional information regarding copyright ownership.  The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy of
the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.
```