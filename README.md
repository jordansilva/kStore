# kStore
A small scale shopping app written in Kotlin!

This app presents a small of products on the main screen, a product detail page when you click on one of the items from the main screen and the shopping cart screen. The project was developed using Kotlin, Android Jetpack Components (LiveData, ViewModel), Kotlin Coroutines and Flow.
In this project, I tried to apply concepts from Clean Architecture to build an easy and testable project. To speed up the implementation I added some libraries in the project - i.e., Koin, Kotlin Coroutines.
     
Pitfalls & Known bugs
--------
Due to the constraint of work only 2 days on this project, I have to do some considerations of what I left out:
- Toast product added to your cart is showing when going back from Shopping cart to Product page again. (Fragment observes LiveData again and it emits the last value. It could be solved with SingleLiveEvent for example.)
- Tests are not covering all View Models and Views. To compensate and mitigate possible issues, I implemented two UI tests end to end. 
- Improve overall the design.
- All products retrieved should have the same currency right now, otherwise it will sum different prices in currencies.
- Implement Unit Tests for View Models and missing classes.
- Implement tests using different files as input. I haven't prioritize that so far.  
- Implement UI Tests
- Improve Design

Improvements & next steps
--------
- Increase the test coverage and refactor the tests to follow some standard (which it's kinda messy now)
- Remove android dependencies (e.g., implementing better interfaces) to make the tests less dependent on Android architecture.  

#### Project structure
--------
- *data* - is where we keep the repository and repository model classes implementations. A repository is where the app can fetch local or remote data.
- *di* - we can find the **module** setup used by Koin.
- *domain* - is the folder where you find the UseCases, Domain Models and Repository interfaces. In this layer you should build your business logic and handle business errors.
- *ui* - is the folder where you can find the Activities, Fragments, ViewModels and Adapters.
- *util* - as the name said, is the place where we can add classes and helpers to be used throughout the project. In this folder, you find Constants, Extensions, Helpers.

#### Architecture
---
I applied MVVM architecture and applied concepts from Clean Architecture, adding an intermediate UseCase between the View Model and Data Layer. Despite this project being small, I applied some of these concepts to make it easily scalable.  
Also, another good practice is by caching the API responses locally and using both remote and local repository (keeping the local repository updated), so I just stored the data in local variables in a singleton data source (I could store on a database, but I didn't have time to do it).   

#### Tests
---
I have implemented some tests for the use cases, Cart class, 1 VM, and some not very useful for repository. After all, I did not implement as many tests as I would like to.
I tried to use fake implementation instead mocking and started applying TDD, but at the 2nd day things got out of control, so... first class, after tests. :(

To execute the UI tests, you should start an android emulator with internet connection and run the command below inside the root project folder. :) 
```bash
./gradlew connectedCheck
```

To execute the unit tests, you should run the command below:
```bash
./gradlew test
```

- Libraries
Some of the libraries that were used in this project aim to facilitate and speed-up the implementation of the test. 
- **Koin.io** - is a *lightweight dependency injection (DI) framework* for Kotlin. It is being used in the project to simplify the DI for ViewModels/Repositories/Tests.
- **Kotlin Coroutines** - is employed in this project for asynchronous programming and running process in the background thread.
- **Mockito** - is a mocking framework for and it is being widely used through the unit tests to provide mocks and stub methods.
- **Lottie** - is a library to parse Adobe After Effects animations and renders it natively. It was used to display empty state animations.

Most of the libraries are not necessary for this small project, however, their use reduces boilerplate from our code as well as speed up the development. 
For instance, by using Koin in this project, we could avoid ViewModel factory classes also helped us with dependency injections by writing a few lines of code.

Compatibility
--------
*minSdkVersion* 21 (Lollipop - Android 5.0)  
*targetSdkVersion* 30 (Android 11)
