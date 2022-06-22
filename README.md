# MobileCodeChallenge
An android app that shows vehicle live location, rider current & drop off location as well as
intermediate co-rider's location after user booked the ride.
-- It shows a UI for the live state of user
-- It also shows the riders current & drop off address

#Steps to build and run the project
-- Download the zip file 
-- Extract zip file to project location
-- Open Android Studio & go to File -> New -> Import project -> Select project location & click OK
-- It will take some time to build the project
-- Connect any android device or open emulator
-- Click on run button to install the project

# Architecture:
## MVVM pattern with Clean architecture developed with Kotlin.
For architecture, I have decided to implement the feature with clean architecture with MVVM:
![clean](https://user-images.githubusercontent.com/10473282/152679625-d15e6b5c-a2ff-4b00-9737-655d20e3fcae.png)

Clean architecture consists of three layers:
- **Data**, includes data objects, network clients, repositories.
- **Domain**, includes use cases of business logic. This layer orchestrates the flow of data from Data Layer to Presentation and the other way.
- **Presentation**, includes UI related components, such as ViewModels, Fragments, Activities.
- **DI**-, includes module for providing dependencies for network and repositories 

Each layer has its own entities/models which are specific to that package. Mapper is used for 
conversion of one layer to another.

## Screenshots:
https://user-images.githubusercontent.com/11865005/170562847-a04fd30a-0cd3-4abc-8088-c9110fbbd8b9.png
https://user-images.githubusercontent.com/11865005/170563596-b2199075-5dfa-4ea9-9fe3-ba245197f178.mp4


##### Libraries:
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android), an easy-to-use DI framework.

- [Coroutines](https://developer.android.com/kotlin/coroutines) For Flow API implementation and async call

- [ktor-websockets](https://ktor.io/docs/websocket.html) Ktor supports the WebSocket protocol and allows you to create applications that require real-time data transfer from and to the server
  - Light weight library
  - Easy to implement
  - In build kotlin flow supported

- [Truth](https://truth.dev/), a library for performing assertions in tests

- [play-services-maps](https://developers.google.com/android/guides/setup), Used for google maps
