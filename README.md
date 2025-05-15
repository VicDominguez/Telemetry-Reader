<div align="center">
    <img src="app/src/main/play_store_512.png" width="75" height="75" />
    <h3 align="center">Telemetry Reader</h3>
    <p>Sample Android app to read and plot telemetry data using Jetpack Compose.</p>
</div>

## Table of Contents

- [🤔 What is this project?](#-what-is-this-project)
- [🎯 Key Features](#-key-features)
- [🚀 Getting Started](#-getting-started)
- [💙 Contributing](#-contributing)
- [ ⚠️ Disclaimer](#-disclaimer)

## 🤔 What is this project?

This project is a playground application to learn Jetpack Compose, Material Design 3 and Kotlin, with the *excuse* of plotting telemetry data from CSV files. This application showcases: 

- Layouts: Column, Row, Box, and ConstraintLayout for structuring UI elements.

- Material Components: Button, Card, TextField, Snackbar, TopAppBar, and more, adhering to Material Design guidelines.

- State Management: Utilization of remember, mutableStateOf, and LaunchedEffect for managing UI state.

- Charts: line charts using Vico library.

This playground is a valuable resource for developers looking to explore and understand Jetpack Compose's capabilities in building modern
Android UIs.

## 🎯 Key Features

- Line chart plotting of csv data. User can select what measure to plot, followed by instant-refesh of what is shown. Y-Axis scale follows data range, while X-axis display timestamp of the measure on HH:mm:ss format.

<img title="Dark chart" src="images/Dark chart.png" alt="App chart in dark mode" width="250" data-align="center">

- Moreover, user can click on each measure to check the exact data.

<img title="Dark chart clickable" src="images/Dark chart clickable.png" alt="Clicked app chart in dark mode" width="250" data-align="center">

- CSV reader and parse, being able to scan all csv local files and plotting them in a Lazy Column to let user select any of them.

<img title="Files" src="images/Files.png" alt="Files Screenshot" width="250" data-align="center">

- Light/dark responsive UI, following mobile mode.

<img title="Chart" src="images/Chart.png" alt="App chart" width="250" data-align="center">

- Simple menu to start navigating
  
  <img title="Menu" src="images/Menu.png" alt="Menu Screenshot" width="250" data-align="center">

## 🚀 Getting Started

Code can compiled in Android Studio without any extra dependency.

## 💙 Contributing

Any contributions you make are **greatly appreciated**, so if you have any idea of how to make this project better, please [create a pull request](https://github.com/VicDominguez/Telemetry-Reader/pulls). Also if you find any bug, please [create an issue](https://github.com/VicDominguez/Telemetry-Reader/issues/new).

## ⚠️ Disclaimer

This project was originally created on 2023 by [@VicDominguez](https://github.com/VicDominguez) together with ETSISI-UPM (School of Computer Systems Engineering, Polytechnic University of Madrid), to be used as programming playground by 15-16 years old students (Sophomore course in USA, 11th year in UK, 4th ESO in Spain).