# PopularMovies Stage 1

- Popular Movies app for Udacity Android Developer Nanodegree
- This app retrieves data from [The Movie DB](https://www.themoviedb.org)

Libraries are used in this Project are:

- Appcombat
- RecyclerView
- Preferences
- ViewModel and LiveData
- Butterknife
- Picasso
- Retrofit
- GSON & Converter



## Common Project Requirements Stage 1

App is written solely in the Java Programming Language.
Movies are displayed in the main layout via a grid of their corresponding movie poster thumbnails.
UI contains an element (i.e a spinner or settings menu) to toggle the sort order of the movies by: most popular, highest rated.
UI contains a screen for displaying the details for a selected movie.
Movie details layout contains title, release date, movie poster, vote average, and plot synopsis.
App utilizes stable release versions of all libraries, Gradle, and Android Studio.

## User Interface - Function

When a user changes the sort criteria (“most popular and highest rated”) the main view gets updated correctly.
When a movie poster thumbnail is selected, the movie details screen is launched.

## Network API Implementation

In a background thread, app queries the /movie/popular or /movie/top_rated API for the sort criteria specified in the settings menu.


## License
```php
Copyright 2017 The Android Open Source Project, Inc.
Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
```
