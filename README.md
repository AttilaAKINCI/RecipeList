# recipeList
Android Kotlin ContentFul API sample

[APK Link](https://drive.google.com/file/d/1PNPid85km2B33vMDHO9j3l-TAD2WCbsn/view?usp=sharing)

#### This project scope covers the substances below
* Kotlin 
* Single activity multiple fragment approach
* Offline usage capability
* MVVM design parttern
* Graph Scoped ViewModels
* Databinding
* ROOM Databases
* Timber API for client side logging 
* Navigation Component
* View Transitions in Navigation Component
* ContentFul API (includes Retrofit API)
* Kotlin coroutines
* Live Data
* Single source of thruth strategy
* Suspend functions and LiveDataScopes
* RecylerView with ListAdapter and DiffUtil
* Facebook shimmer on listing page
* Glide for picture loading over internet
* Repository pattern for View Models
* Test case sample development

#### Possible future innovations after more functionality
* Dependency injection with HILT
* Search capability for recyclerList
* Paging for recyclerlist using JetPack Paging library

#### Application exposition
Main goal is creating a Recipe Listing page and also an another page so as to view some details about it. 

Application initiates with a splash screen. After splash animation listing page has been shown. 

Listing page has a list insfrastructure so as to display recipes. The data have been fetched via contentful api. Considering to network delay, list loading animation has been provided by facebook shimmer library. 

Data is evaluated with single source of thruth strategy. It means that the data which is fetched from internet (this case from ContentFul API), is stored in ROOM databases on the phone. During listing fused data is used for listing. Listing rows shows recipe image and recipe name on the list.

Click action of each row navigates user to detail page of these recipes with view transion animation.

In Detail Page, user can view additional information about recipes (recipe name, recipe visuals, chef name, calories, some tags, description)

#### ScreenShoots

<img src="https://github.com/AttilaAKINCI/recipeList/blob/main/app/screenshots/device-2021-03-28-183055.png" width="231">   <img src="https://github.com/AttilaAKINCI/recipeList/blob/main/app/screenshots/device-2020-10-04-181945.png" width="200">   <img src="https://github.com/AttilaAKINCI/recipeList/blob/main/app/screenshots/device-2020-10-04-182003.png" width="200">   <img src="https://github.com/AttilaAKINCI/recipeList/blob/main/app/screenshots/device-2020-10-04-182014.png" width="200"> <img src="https://github.com/AttilaAKINCI/recipeList/blob/main/app/screenshots/device-2021-03-28-182952.png" width="231"> 


