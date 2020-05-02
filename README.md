<p align="center">
<img src="https://github.com/sssurvey/reader/blob/develop/readmeRes/read_banner.png?raw=true" alt="add source screenshot with half dark mode" height="200 px" style="margin:0px 10px"/>
</p>

# Reader
The reader application is a client application for the website <https://theoldreader.com/feeds>. Currently the app version is ```v0.2.7``` and you can see all you subscription list, and read articles with it.

<p align="center">
<img src="https://github.com/sssurvey/reader/blob/documentation-better-read-me/readmeRes/sources_list.png?raw=true" alt="source list screenshot with half dark mode" height="400px" style="margin:10px 10px"/>

<img src="https://github.com/sssurvey/reader/blob/documentation-better-read-me/readmeRes/add_articles.png?raw=true" alt="add source screenshot with half dark mode" height="400px" style="margin:10px 10px"/>

<img src="https://github.com/sssurvey/reader/blob/develop/readmeRes/article_details.png?raw=true" alt="read article details webview" height="400px" style="margin:10px 10px"/>
</p>

The application is written in 100% ```Kotlin``` and is utilizing the library:

- Retrofit 2: For the communication with The Old Reader API
- RxJava 2: For concurrency
- Dagger 2: For dependency Injection
- Room database: For offline reading

The Reader application will be made open source:

[![Open Source Love svg1](https://badges.frapsoft.com/os/v1/open-source.svg?v=103)](https://github.com/ellerbrock/open-source-badges/)
[![License](https://img.shields.io/badge/License-BSD%202--Clause-orange.svg)](https://opensource.org/licenses/BSD-2-Clause) 