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

  <img src="https://github.com/user-attachments/assets/b7ab245c-685a-446e-827e-f29f47e61c0a" width="250" heigth="250">


---

### Flujo Principal y Estados

A continuación se muestra el flujo de búsqueda, la pantalla de detalle y la gestión de errores de red.

| Pantalla de Búsqueda | Pantalla de Detalle (Vertical) | Error de Conexión |
| :---: | :---: | :---: |
| <img src="URL_DE_TU_IMAGEN_DE_BUSQUEDA" width="250"> | <img src="URL_DE_TU_IMAGEN_DE_DETALLE" width="250"> | <img src="URL_DE_TU_IMAGEN_DE_ERROR" width="250"> |

---

### Diseño Responsivo

La pantalla de detalle se adapta a la orientación horizontal para aprovechar mejor el espacio.

<p align="center">
  <img src="URL_DE_TU_IMAGEN_DE_DETALLE_HORIZONTAL" width="515">
</p>
