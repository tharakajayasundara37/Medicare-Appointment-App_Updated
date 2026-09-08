# MediCare — Smart Healthcare Companion

MediCare is a native Android healthcare appointment and patient-management app for Sri Lanka. It combines an offline-first clinic workflow with a modern patient-facing service hub.

## Included

- Doctor discovery by name, speciality, or hospital
- Appointment creation, history, search, update, cancellation, and SMS confirmation
- Patient and family health profiles
- Lab test and home-collection request flow
- Prescription medicine delivery request flow
- Video consultation entry flow
- Preventive health check-up packages
- One-tap 1990 emergency assistance
- Local SQLite persistence and a responsive Material 3 interface

Lab, pharmacy, hospital schedules, online payments, authentication, and video calls require production partner APIs. The app presents these honestly as integration-ready flows rather than simulated transactions.

## Build

Open the project with Android Studio and run the `app` configuration, or use:

```powershell
.\gradlew.bat testDebugUnitTest assembleDebug
```

Minimum Android version: Android 7.0 (API 24).
