# BoldSky Weather App 🌦️

*Una aplicación meteorológica limpia, moderna y robusta construida con Jetpack Compose y las mejores prácticas de la arquitectura Android.*

## 🚀 Cómo Empezar

1.  Clona el repositorio ejecutando el siguiente comando.
   ```console
git clone git@github.com:pipearcos221/BoldSky.git
```
3.  Abre el proyecto en Android Studio.
4.  Añade tu clave de API de [WeatherAPI](https://www.weatherapi.com/) en el archivo `secrets.properties`, en el caso de que no exista debe crearlo en el directorio raiz del proyecto con la siguiente linea de codigo
  ```properties
    API_KEY="YOUR_API_KEY"
  ```

  debera reemplazar `"YOUR_API_KEY"` por la key proveida por [WeatherAPI](https://www.weatherapi.com/).


5.  Ejecute el proyecto mediando el boton de Run ▶️

## ✨ Características

*   **Búsqueda de Ciudades en Tiempo Real**: Encuentra información meteorológica para cualquier ciudad del mundo.
*   **Detalles del Clima Completos**:
    *   Temperatura actual y sensación térmica.
    *   Condiciones meteorológicas con iconos dinámicos.
    *   Métricas detalladas: humedad, viento, visibilidad, etc.
    *   Pronóstico por horas y por días.
*   **Interfaz de Usuario Pulida**:
    *   Construida 100% con Jetpack Compose.
    *   Tema oscuro inmersivo y consistente.
    *   Diseño responsivo que se adapta a orientaciones vertical y horizontal.
    *   Animaciones fluidas y una pantalla de bienvenida atractiva.
*   **Gestión de Errores Inteligente**:
    *   Un monitor de red proactivo que detecta la pérdida de conexión al instante.
    *   Pantallas de error contextuales que distinguen entre problemas de red, de servidor o desconocidos, con opción de reintentar.

## 🏗️ Arquitectura y Tech Stack

Este proyecto sigue los principios de **Clean Architecture** y las guías de arquitectura recomendadas por Google.

*   **UI**: Jetpack Compose, Material 3, Coil (para imágenes), Lottie (para animaciones).
*   **Arquitectura**: Model-View-ViewModel (MVVM) con flujos de UI (`StateFlow`).
*   **Inyección de Dependencias**: Hilt para gestionar las dependencias en toda la aplicación.
*   **Asincronía**: Kotlin Coroutines y Flow para gestionar las operaciones en segundo plano.
*   **Red**: Retrofit para las llamadas a la API, OkHttp para la configuración del cliente y Kotlinx.Serialization para parsear JSON.
*   **Testing**:
    *   **Tests Unitarios**: JUnit 4 y MockK para probar la lógica de los `ViewModel`s (`StandardTestDispatcher` para corrutinas).
*   **Modularización**: Proyecto multi-módulo para una clara separación de responsabilidades.

## 📂 Estructura de Archivos

El proyecto está organizado en una arquitectura multi-módulo, promoviendo la escalabilidad y una clara separación de responsabilidades.
```
BoldSky/
├── app/                      # Módulo principal de la aplicación (MainActivity, Navegación)
├── core:network/             # Capa de red (Retrofit, OkHttp, Monitor de Red)
├── core:ui/                  # Design System y componentes de UI compartidos
├── feature:search/
│   ├── data/                 # DTOs, API Service, Repositorio
│   ├── domain/               # Modelos de dominio y Casos de Uso
│   └── presentation/         # Screen, ViewModel, y Estado de la UI
├── feature:detail/
    ├── data/                 # DTOs, API Service, Repositorio
    ├── domain/               # Modelos de dominio y Casos de Uso
    └── presentation/         # Screen, ViewModel, y Estado de la UI
```

## 📱 Pantallas

A continuación se muestran algunas de las pantallas y animaciones clave de la aplicación, demostrando su diseño limpio y su funcionalidad en diferentes escenarios.

### Pantalla de Bienvenida Animada

La aplicación recibe al usuario con una animación fluida construida con Lottie, creando una primera impresión moderna y atractiva.


https://github.com/user-attachments/assets/a60bb970-6afa-44f9-8c2f-b3d72401969b




---

### Flujo Principal y Estados

A continuación se muestra el flujo de búsqueda, la pantalla de detalle y la gestión de errores de red.

| Pantalla de Búsqueda | Pantalla de Detalle (Vertical) |
| :---: | :---: |
| <img width="280" height="580" alt="image" src="https://github.com/user-attachments/assets/985918ad-1f04-4eb6-91c7-9fb9fd7813e6" /> | <video width="280" height="580" alt="video" src="https://github.com/user-attachments/assets/9377d1e7-0d97-430b-be4f-79cecfcf55be" /> 

| Error de Conexión | Error de Servidor | Error Desconocido | 
| :---: | :---: | :---: |
| <img width="280" height="580" alt="image" src="https://github.com/user-attachments/assets/b72f06e6-dfbf-4a5f-b930-af4221eed3e7" /> | <img width="280" height="580" alt="image" src="https://github.com/user-attachments/assets/3f01eea7-f44a-41de-a014-4e287656ce76" /> | <img width="280" height="580" alt="image" src="https://github.com/user-attachments/assets/154760c4-bb0d-4a52-9d09-8ea7282728c9" /> |


### Diseño Responsivo

La pantalla de detalle se adapta a la orientación horizontal para aprovechar mejor el espacio.

| Pantalla de Búsqueda LandScape |
| :---: |
| <video width="850" height="700" alt="video" src="https://github.com/user-attachments/assets/b75f7bf4-1513-4caa-86f6-89fcff67ad37" /> |


| Pantalla de Detalle LandScape |
| :---: |
| <video width="850" height="700" alt="video" src="https://github.com/user-attachments/assets/df3db796-26ba-4b4d-928a-26b19f6fb718" /> | 

## 🤖 Integración Continua (CI)

Este proyecto utiliza un pipeline de Integración Continua (CI) con **GitHub Actions** para automatizar la verificación de la calidad del código en cada cambio. Esto garantiza que la base de código se mantenga estable, funcional y limpia, previniendo la introducción de nuevos errores.

### Flujo de Trabajo del Pipeline

El pipeline se ejecuta automáticamente en cada `push` y `pull_request` a las ramas principales (como `main`, `chore` y `feature`), y realiza los siguientes trabajos:

1.  **Compilación (`Build`)**: El primer paso es compilar el código completo de la aplicación (`./gradlew assembleDebug`). Esto asegura que no hay errores de sintaxis o de dependencias que rompan el proyecto.

2.  **Análisis Estático (`Linting`)**: Se ejecuta un análisis estático del código con **Detekt** (`./gradlew detekt`). Este trabajo busca "code smells", posibles bugs, y desviaciones de las guías de estilo de Kotlin, manteniendo el código limpio y legible.

3.  **Pruebas Unitarias (`Unit Tests`)**: Se ejecutan todos los tests unitarios del proyecto (`./gradlew testDebugUnitTest`). Esto verifica que la lógica de negocio en los `ViewModel`s y otras clases funciona como se espera, de forma rápida y aislada.



