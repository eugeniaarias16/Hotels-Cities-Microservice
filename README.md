# 🌐 Tourism & Hotels Management System
## Arquitectura de Microservicios con Spring Cloud & Docker

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.4-brightgreen)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.0.0-blue)
![Docker](https://img.shields.io/badge/Docker-Compose-blue)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)

Sistema completo de gestión turística implementado con **microservicios**, **Spring Cloud**, **Docker** y patrones de resiliencia avanzados.

---

## 📋 Tabla de Contenidos

- [🏗️ Arquitectura del Sistema](#️-arquitectura-del-sistema)
- [🛠️ Tecnologías Implementadas](#️-tecnologías-implementadas)
- [🚀 Inicio Rápido](#-inicio-rápido)
- [📊 Base de Datos](#-base-de-datos)
- [🔍 API Endpoints](#-api-endpoints)
- [⚡ Circuit Breaker Testing](#-circuit-breaker-testing)
- [📄 Documentación API](#-documentación-api)
- [🐛 Troubleshooting](#-troubleshooting)

---

## 🏗️ Arquitectura del Sistema

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Client Apps   │    │   Postman/APIs  │    │   Swagger UI    │
└─────────┬───────┘    └─────────┬───────┘    └─────────┬───────┘
          │                      │                      │
          └──────────────────────┼──────────────────────┘
                                 │
                    ┌─────────────▼───────────────┐
                    │      API Gateway            │
                    │      Port: 8080             │
                    │   (Spring Cloud Gateway)    │
                    └─────────────┬───────────────┘
                                  │
                     ┌────────────▼────────────┐
                     │    Eureka Server        │
                     │    Port: 8761           │
                     │  (Service Discovery)    │
                     └─────────────────────────┘
                                  │
        ┌─────────────────────────┼─────────────────────────┐
        │                         │                         │
┌───────▼───────┐        ┌────────▼────────┐       ┌────────▼────────┐
│ Cities Service │        │ Hotels Service  │       │  MySQL Database │
│   Port: 8081   │◄──────►│   Port: 8082    │       │   Port: 3307    │
│   (CRUD Ops)   │ Feign  │ (Search & Info) │       │  (Data Storage) │
└────────────────┘        └─────────────────┘       └─────────────────┘
```

### 🔧 Componentes Principales

| Componente | Puerto | Función | Tecnología |
|------------|--------|---------|------------|
| **API Gateway** | 8080 | Enrutamiento, Load Balancing | Spring Cloud Gateway |
| **Eureka Server** | 8761 | Service Discovery | Netflix Eureka |
| **Cities Service** | 8081 | CRUD Ciudades | Spring Boot + JPA |
| **Hotels Service** | 8082 | Búsquedas Hoteles | Spring Boot + JPA |
| **MySQL Database** | 3307 | Persistencia de Datos | MySQL 8.0 |

---

## 🛠️ Tecnologías Implementadas

### Spring Cloud Stack
- **🌐 Spring Cloud Gateway**: API Gateway con enrutamiento dinámico
- **🔍 Netflix Eureka**: Service Discovery y Registry
- **🔄 OpenFeign**: Comunicación entre microservicios
- **⚡ Resilience4j**: Circuit Breaker pattern
- **📊 Spring Boot Actuator**: Monitoreo y métricas

### Base Technology Stack
- **☕ Java 21**: Versión LTS más reciente
- **🍃 Spring Boot 3.5.4**: Framework principal
- **🐘 MySQL 8.0**: Base de datos relacional
- **🐳 Docker & Docker Compose**: Contenedorización
- **📚 Lombok**: Reducción de boilerplate code
- **📖 SpringDoc OpenAPI**: Documentación automática

### Patrones Implementados
- **Microservices Architecture**: Separación de responsabilidades
- **Circuit Breaker Pattern**: Tolerancia a fallos
- **Service Discovery Pattern**: Registro automático de servicios
- **API Gateway Pattern**: Punto único de entrada
- **Database Per Service**: Aislamiento de datos

---

## 🚀 Inicio Rápido

### Prerrequisitos
```bash
# Verificar instalaciones
java --version    # Java 21+
docker --version  # Docker 20.0+
docker-compose --version
```

### 1️⃣ Clonar Repositorio
```bash
git clone <tu-repositorio>
cd Hotels-Cities
```

### 2️⃣ Configurar Variables de Entorno
Crear archivo `.env` en la raíz del proyecto:
```bash
# Ejemplo de configuración - Ajustar según tu entorno
SQL_USERNAME=root
SQL_PASSWORD=password123
```

### 3️⃣ Levantar el Sistema Completo
```bash
# Construir y ejecutar todos los servicios
docker-compose up --build

# Para ejecutar en background
docker-compose up -d --build
```

### 4️⃣ Verificar Servicios
```bash
# Eureka Dashboard
http://localhost:8761

# API Gateway Health
http://localhost:8080/actuator/health

# Swagger UIs
http://localhost:8081/swagger-ui/index.html  # Cities Service
http://localhost:8082/swagger-ui/index.html  # Hotels Service
```

### ⚠️ Orden de Inicio Recomendado
Si levantas servicios individualmente:
1. **Eureka Server** (8761)
2. **API Gateway** (8080)
3. **MySQL Database** (3307)
4. **Cities Service** (8081)
5. **Hotels Service** (8082)

---

## 📊 Base de Datos

### 🏗️ Inicialización Automática
Los datos se cargan automáticamente al iniciar cada servicio:

**Cities Service** (`CitiesApplication.java`):
```java
@Override
public void run(String... args) throws Exception {
   if(cityRepository.count()==0){
      System.out.println("Loading city data from file CSV...");
      loadCitiesFromCSV();
      System.out.println("City data successfully uploaded.");
   }else {
      System.out.println("City data already exists, skipping load.");
   }
}
```

**Hotels Service**: Implementación similar para `hotelsBD.csv`

### 📈 Contenido de la Base de Datos
- **🏙️ 100 Ciudades**: Principalmente Japón (Tokio, Osaka, Kioto, Sapporo) y Estados Unidos
- **🏨 137 Hoteles**: Calificaciones de 3-5 estrellas en destinos turísticos principales
- **🌏 Cobertura**: Japón (regiones Kanto, Kansai, Hokkaido) y estados de EE.UU.

### 🗂️ Archivos de Datos Disponibles
```
docs/BD/
├── citiesBD.csv    # 100 ciudades turísticas
└── hotelsBD.csv    # 137 hoteles con ratings
```

---

## 🔍 API Endpoints

### 🌐 Base URLs
- **API Gateway**: `http://localhost:8080`
- **Cities Direct**: `http://localhost:8081`
- **Hotels Direct**: `http://localhost:8082`

### 🏙️ Cities Management (CRUD Completo)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/cities-service/tourism/cities` | Obtener todas las ciudades |
| `GET` | `/cities-service/tourism/city/id/{id}` | Buscar ciudad por ID |
| `GET` | `/cities-service/tourism/city/name/{name}` | Buscar ciudad por nombre |
| `POST` | `/cities-service/tourism/city/create` | Crear nueva ciudad |
| `PUT` | `/cities-service/tourism/city/{id}` | Actualizar ciudad |
| `DELETE` | `/cities-service/tourism/city/{id}` | Eliminar ciudad |

### 🏨 Hotels Management (Búsquedas Avanzadas)

#### 🔍 Búsqueda por Detalles
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/hotels-service/hotels/id/{id}` | Buscar hotel por ID |
| `GET` | `/hotels-service/hotels/name/{name}` | Buscar hotel por nombre |
| `GET` | `/hotels-service/hotels/address/{address}` | Buscar hotel por dirección |

#### 📍 Búsqueda por Ubicación
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/hotels-service/hotels/city/{city}` | Hoteles por ciudad |
| `GET` | `/hotels-service/hotels/state/{state}` | Hoteles por estado |
| `GET` | `/hotels-service/hotels/country/{country}` | Hoteles por país |

#### ⭐ Búsqueda por Calidad
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/hotels-service/hotels/stars/{stars}` | Hoteles por calificación (3-5) |

#### 🔧 Búsqueda Avanzada Multi-Filtro
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/hotels-service/hotels/filters?city=X&state=Y&country=Z&stars=N` | Filtros combinados |

### 🔗 Cross-Service Integration
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/cities-service/tourism/city/{city}/hotel` | Hoteles por ciudad (via Tourism) |
| `GET` | `/cities-service/tourism/state/{state}/hotel` | Hoteles por estado (via Tourism) |
| `GET` | `/cities-service/tourism/country/{country}/hotel` | Hoteles por país (via Tourism) |
| `GET` | `/cities-service/tourism/hotel/search?...` | Búsqueda avanzada (via Tourism) |

---

## ⚡ Circuit Breaker Testing

### 🎯 Endpoints Especiales para Testing
**⚠️ Endpoints predestinados a fallar para probar Circuit Breaker:**

| Servicio | Endpoint | Propósito |
|----------|----------|-----------|
| **Tourism** | `/cities-service/tourism/hotels/errorId/{id}` | Prueba CB en Cities Service |
| **Hotels** | `/hotels-service/hotels/errorId/{id}` | Prueba CB en Hotels Service |

### 🔬 Cómo Probar Circuit Breaker

1. **Configuración Implementada**:
   ```java
   // Ejemplo de configuración en IHotelRepository
   @FeignClient(name="hotels-service")
   public interface IHotelRepository {
   
       @CircuitBreaker(name = "hotels-service", fallbackMethod = "fallbackFindHotelsById")
       @Retry(name="hotels-service")
       @GetMapping("hotels/errorId/{id}")
       HotelsDTO findHotelsById(@PathVariable("id") Long id);
       
       // Métodos fallback implementados como default methods
       default HotelsDTO fallbackFindHotelsById(Long id, Throwable ex){
           logger.warn("🔴 RESILIENCE4J FALLBACK - findHotelsByID para ID: {} por excepción: {}", 
                       id, ex.getMessage());
           return null;
       }
   }
   ```

2. **Proceso de Testing**:
   ```bash
   # 1. Llamar endpoint de error múltiples veces
   curl http://localhost:8080/cities-service/tourism/hotels/errorId/999
   
   # 2. Observar logs para ver:
   # - Primeras llamadas fallan con error real
   # - Circuit se abre después del threshold
   # - Llamadas siguientes ejecutan fallback más rápido
   ```

3. **Comportamiento Esperado**:
    - **Estado CLOSED**: Llamadas normales pasan
    - **Estado OPEN**: Fallas rápidas con fallback
    - **Estado HALF-OPEN**: Prueba de recuperación automática

4. **Logs del Fallback**:
   ```
   🔴 RESILIENCE4J FALLBACK - findHotelsByID para ID: 999 por excepción: [error details]
   ```

### 📊 Monitoreo de Circuit Breaker
```bash
# Ver estado de circuit breakers
curl http://localhost:8080/actuator/circuitbreakers
```

### ⚠️ Nota Importante sobre Fallback Methods
**Issue conocido**: Los métodos fallback están actualmente implementados dentro de la interfaz `IHotelRepository`. En futuras versiones se refactorizará para usar clases separadas debido a conflictos entre OpenFeign y Resilience4j al referenciar clases externas.

---

## 📄 Documentación API

### 📚 Colecciones Postman Incluidas
```
docs/apis/
├── gateway_collection.json     # Colección completa via Gateway
├── cities-docs.json           # Endpoints específicos de Cities
├── hotels-docs.json           # Endpoints específicos de Hotels
└── tourism_hotels.json        # Integración cross-service
```

### 🔄 Importar en Postman
1. Abrir Postman
2. Importar → Seleccionar `gateway_collection.json`
3. Configurar variables de entorno si es necesario
4. ¡Listo para testing!

### 📖 Swagger UI
- **Cities Service**: http://localhost:8081/swagger-ui/index.html
- **Hotels Service**: http://localhost:8082/swagger-ui/index.html

### 🧪 Ejemplos de Testing

#### Buscar Ciudad por Nombre
```bash
curl -X GET "http://localhost:8080/cities-service/tourism/city/name/Tokio"
```

#### Buscar Hoteles por Ciudad
```bash
curl -X GET "http://localhost:8080/hotels-service/hotels/city/Tokio"
```

#### Cross-Service: Hoteles via Tourism Service
```bash
curl -X GET "http://localhost:8080/cities-service/tourism/city/Tokio/hotel"
```

#### Búsqueda Avanzada con Filtros
```bash
curl -X GET "http://localhost:8080/hotels-service/hotels/filters?city=Tokio&state=Kanto&country=Japón&stars=5"
```

---

## 🐛 Troubleshooting

### 🔧 Problemas Comunes

#### 1. **Error de Conexión a MySQL**
```bash
# Verificar que MySQL esté corriendo
docker-compose ps mysql

# Ver logs de MySQL
docker-compose logs mysql

# Verificar variables de entorno
echo $SQL_USERNAME
echo $SQL_PASSWORD
```

#### 2. **Servicios No Se Registran en Eureka**
```bash
# Verificar Eureka primero
curl http://localhost:8761/eureka/apps

# Verificar logs del servicio
docker-compose logs cities-service
docker-compose logs hotels-service
```

#### 3. **Gateway No Enruta Correctamente**
```bash
# Verificar rutas configuradas
curl http://localhost:8080/actuator/gateway/routes

# Verificar salud del gateway
curl http://localhost:8080/actuator/health
```

#### 4. **Circuit Breaker No Funciona**
```bash
# Verificar configuración de CB
curl http://localhost:8080/actuator/circuitbreakers

# Ver logs específicos
docker-compose logs cities-service | grep "RESILIENCE4J"
```

### 🔄 Comandos Útiles

#### Reiniciar Sistema Completo
```bash
docker-compose down
docker-compose up --build
```

#### Reiniciar Solo un Servicio
```bash
docker-compose restart cities-service
```

#### Ver Logs en Tiempo Real
```bash
docker-compose logs -f cities-service
```

#### Limpiar Volúmenes (Resetear BD)
```bash
docker-compose down -v
docker-compose up --build
```

### 📞 Puertos y Servicios

| Puerto | Servicio | URL de Verificación |
|--------|----------|-------------------|
| 8761 | Eureka Server | http://localhost:8761 |
| 8080 | API Gateway | http://localhost:8080/actuator/health |
| 8081 | Cities Service | http://localhost:8081/actuator/health |
| 8082 | Hotels Service | http://localhost:8082/actuator/health |
| 3307 | MySQL | Conexión por cliente DB |

---

## 📄 Licencia

Este proyecto está bajo la licencia MIT. Ver el archivo `LICENSE` para más detalles.

---

## 👨‍💻 Autor

**TEugenia M.Arias**
- GitHub: [@eugeniaarias16](https://github.com/eugeniaarias16)
- LinkedIn: [Eugenia Maria Arias](https://linkedin.com/in/eugenia-maria-arias/)

---

## 🚀 Próximas Mejoras

- [ ] Refactorizar fallback methods a clases separadas
- [ ] Implementar Config Server para centralizar configuración
- [ ] Añadir métricas con Micrometer y Prometheus
- [ ] Implementar autenticación con Spring Security
- [ ] Añadir tests de integración completos

---

*¿Encontraste algún problema o tienes sugerencias? ¡Abre un issue o contribuye al proyecto!* 🤝