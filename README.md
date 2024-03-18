# RecipeList
RecipeList is a book that contains amazing recipes for you via Contentful API !! 

> [!NOTE]
> Please be aware !! This repository ontains legacy implementations and arhitectures.
> It's no longer possible to refactor, modernize due to insufficient data source. it's arhieved and won't be supperted in the future.

[APK Link (https://drive.google.com/file/d/1lHpgAixNPsk-dwVi8I_msLdweminSHUE/view?usp=sharing)](https://drive.google.com/file/d/1lHpgAixNPsk-dwVi8I_msLdweminSHUE/view?usp=sharing)

<img src="https://user-images.githubusercontent.com/21987335/112758276-1ca16c80-8ff6-11eb-965f-a4649c24c59b.gif" width="400"/>

#### This project scope covers the substances below
* Kotlin 
* Single activity multiple fragment approach
* Offline usage capability
* MVVM design parttern
* Fragment / Activity Scoped ViewModels
* Databinding, ViewBinding
* ROOM Databases
* Timber API for client side logging 
* Navigation Component
* View Transitions in Navigation Component
* ContentFul API (includes Retrofit API)
* Kotlin coroutines
* Live Data
* Lottie animations
* Thrust Libraray for Assertions
* Dependency Injection (HILT)
* Suspend functions and LiveDataScopes
* RecylerView with ListAdapter and DiffUtil
* Facebook shimmer on listing page
* Glide for picture loading over internet
* Repository pattern for View Models
* MockK & JUnit5 for unit Testing 
* JUnit4, HILT usage for integration tests

#### Possible future innovations after more functionality
* Search capability for recyclerList
* Paging for recyclerlist using JetPack Paging library

#### Application exposition
Main goal is creating a Recipe Listing page and also an another page so as to view some details about it. 

Application initiates with a splash screen. After splash animation listing page has been shown. 

Listing page has a list insfrastructure so as to display recipes. The data have been fetched via contentful api. Considering to network delay, list loading animation has been provided by facebook shimmer library. 

Data is fetched from internet (this case from ContentFul API) and is shown in UI and also it is stored in ROOM databases on the phone. Listing rows shows recipe image and recipe name on the list.

In case of network error, data will be fetched from ROOM DB in order to ensure a offline usage for better user experience

Click action of each row navigates user to detail page of these recipes with view transion animation.

In Detail Page, user can view additional information about recipes (recipe name, recipe visuals, chef name, calories, some tags, description)

#### ScreenShoots

<img src="https://github.com/AttilaAKINCI/recipeList/blob/main/app/screenshots/device-2021-03-28-183055.png" width="231">   <img src="https://github.com/AttilaAKINCI/recipeList/blob/main/app/screenshots/device-2020-10-04-181945.png" width="200">   <img src="https://github.com/AttilaAKINCI/recipeList/blob/main/app/screenshots/device-2020-10-04-182003.png" width="200">   <img src="https://github.com/AttilaAKINCI/recipeList/blob/main/app/screenshots/device-2020-10-04-182014.png" width="200"> <img src="https://github.com/AttilaAKINCI/recipeList/blob/main/app/screenshots/device-2021-03-28-182952.png" width="231"> 

# License

The code is licensed as:

```
Copyright 2021 Attila Akıncı

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

