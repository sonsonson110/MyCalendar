# My Calendar
- A project for Mobile Application Development PTIT 2024
- App idea is inspired by Google Calendar (Simplified version)
- Project structure is inspired by [NiaApp on Github](https://github.com/android/nowinandroid)
> **Try my current release apk [HERE](app/release/app-release.apk)**
## Demo video
[![demo video](https://img.youtube.com/vi/dtpa6spcfFw/0.jpg)](https://youtu.be/dtpa6spcfFw)

## Features
- Reminder feature: [Schedule alarms](https://developer.android.com/develop/background-work/services/alarms/schedule) and [notification](https://developer.android.com/develop/ui/views/notifications/build-notification)
- Firebase integration
  - [Authentication](https://firebase.google.com/docs/auth)
  - [Firestore](https://firebase.google.com/docs/firestore/quickstart): store app's remote data
- Develop UI with [Jetpack Compose](https://developer.android.com/develop/ui/compose)
- Follow latest version of Google’s open-source design system [Material 3](https://m3.material.io/)
- Follow MVVM architectural pattern with [ViewModel library](https://developer.android.com/topic/libraries/architecture/viewmodel)
- Dependency Injection with [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- Store persistence data with Jetpack [Room](https://developer.android.com/training/data-storage/room)
- Fetch from internet with [Retrofit](https://github.com/square/retrofit)
  - Weather Api from https://openweathermap.org/api
  - Location Api from https://docs.locationiq.com/api
- Hide secret keys with [Secrets Gradle Plugin](https://github.com/google/secrets-gradle-plugin)

## Project road map
1. Cleaner code, adding a domain layer
2. Synchronize user data
3. Add event features:
   - add someone to an event
   - event live chat...

## Other researches
- Sticky header: https://fvilarino.medium.com/creating-a-sticky-letter-list-in-jetpack-compose-2e7e9c8f9b91
- Data grouping: https://www.youtube.com/watch?v=fG3FjhF2bCg
