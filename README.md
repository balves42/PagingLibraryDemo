# PagingLibraryDemo
Paging Library implementation with Kotlin + RxJava + Retrofit

# Paging Library
Paging Library is part of the Android Architecture Components which maintain effiency and keeps the logic without being complicated. This project was inspired by an [article](https://medium.com/@sharmadhiraj.np/android-paging-library-step-by-step-implementation-guide-75417753d9b9 "Android Paging Library Step By Step Implementation Guide") in which i performed some changes to fulfill my needs

# Implementation
I extended the Model View ViewModel Pattern (MVVM) and added it a Presenter (MVVMP) which i added most of the logic of the app. I also added [Stetho](http://facebook.github.io/stetho/ "Stetho: A debug bridge for Android applications") to have an easier way of checking the content of the requests. For this project it was used the [News API](https://newsapi.org/ "A JSON API for live news and blog articles")

# Demo
<img src="https://i.imgur.com/18Cw9rV.png" title="List with invalidate and error" /></a>
1. Invalidate button for cleaning the data source and making new requests
2. List with news information
3. Error viewholder with retry behaviour

#### Open chrome, go to chrome://inspect/devices and check the requests being made
<img src="https://i.imgur.com/wf1uDuC.png" title="Stetho results" /></a>
