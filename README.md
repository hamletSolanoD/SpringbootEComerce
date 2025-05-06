# 🚀 E-commerce Spring MVC: Arquitectura multicapa para tienda online

![Estado](https://img.shields.io/badge/Estado-Desarrollo-orange) ![Versión](https://img.shields.io/badge/Versi%C3%B3n-1.0.0-blue) ![Framework](https://img.shields.io/badge/Spring%20Boot-3.1.x-brightgreen) ![Java](https://img.shields.io/badge/Java-17-red) ![Tests](https://img.shields.io/badge/Cobertura-80%25-yellow) ![Licencia](https://img.shields.io/badge/Licencia-MIT-lightgrey)

## 📋 Índice

- [Descripción Técnica](https://claude.ai/chat/e6172f34-b78e-4ab5-8faa-0f36679e402d#-descripci%C3%B3n-t%C3%A9cnica)
- [Arquitectura Multicapa](https://claude.ai/chat/e6172f34-b78e-4ab5-8faa-0f36679e402d#-arquitectura-multicapa)
- [Patrones de Diseño Implementados](https://claude.ai/chat/e6172f34-b78e-4ab5-8faa-0f36679e402d#-patrones-de-dise%C3%B1o-implementados)
- [Demostración Visual](https://claude.ai/chat/e6172f34-b78e-4ab5-8faa-0f36679e402d#-demostraci%C3%B3n-visual)
- [Características Principales](https://claude.ai/chat/e6172f34-b78e-4ab5-8faa-0f36679e402d#-caracter%C3%ADsticas-principales)
- [Modelos de Datos](https://claude.ai/chat/e6172f34-b78e-4ab5-8faa-0f36679e402d#-modelos-de-datos)
- [Desglose de Módulos](https://claude.ai/chat/e6172f34-b78e-4ab5-8faa-0f36679e402d#-desglose-de-m%C3%B3dulos)
- [Tecnologías](https://claude.ai/chat/e6172f34-b78e-4ab5-8faa-0f36679e402d#-tecnolog%C3%ADas)
- [Instalación](https://claude.ai/chat/e6172f34-b78e-4ab5-8faa-0f36679e402d#-instalaci%C3%B3n)
- [Contribuir](https://claude.ai/chat/e6172f34-b78e-4ab5-8faa-0f36679e402d#-contribuir)
- [Licencia](https://claude.ai/chat/e6172f34-b78e-4ab5-8faa-0f36679e402d#-licencia)

## 📝 Descripción Técnica

Este proyecto implementa un sistema e-commerce completo basado en una arquitectura por capas MVC (Modelo-Vista-Controlador) utilizando Spring Boot para despliegue en servidores Tomcat. La aplicación se centra en la implementación rigurosa de patrones arquitectónicos y de diseño, junto con buenas prácticas de desarrollo, para crear un sistema robusto, mantenible y extensible para la venta de servicios digitales con distintos planes de suscripción.

La motivación principal del proyecto es demostrar la aplicación práctica de patrones de diseño en un contexto real de negocio, siguiendo los principios SOLID y utilizando Spring Boot como framework de aplicación.

## 🏗️ Arquitectura Multicapa

El proyecto implementa una arquitectura multicapa claramente definida:

```
┌───────────────────────────────────────────────────────┐
│                   Capa de Presentación                │
│  ┌─────────────┐ ┌────────────┐ ┌──────────────────┐  │
│  │ Thymeleaf   │ │ HTML/CSS   │ │ Bootstrap        │  │
│  └─────────────┘ └────────────┘ └──────────────────┘  │
└───────────────────────────────────────────────────────┘
                         ▲
                         │ 
                         ▼
┌───────────────────────────────────────────────────────┐
│                   Capa de Controlador                 │
│  ┌─────────────┐ ┌────────────┐ ┌──────────────────┐  │
│  │ AuthCtrl    │ │ CartCtrl   │ │ DashboardCtrl    │  │
│  └─────────────┘ └────────────┘ └──────────────────┘  │
└───────────────────────────────────────────────────────┘
                         ▲
                         │ 
                         ▼
┌───────────────────────────────────────────────────────┐
│                   Capa de Servicio                    │
│   ┌─────────────┐ ┌────────────┐ ┌────────────────┐   │
│   │ Autenticación│ │ Compras    │ │ Gestión       │   │
│   └─────────────┘ └────────────┘ └────────────────┘   │
└───────────────────────────────────────────────────────┘
                         ▲
                         │ 
                         ▼
┌───────────────────────────────────────────────────────┐
│                 Capa de Repositorio                   │
│   ┌─────────────┐ ┌────────────┐ ┌────────────────┐   │
│   │ UsuarioRepo │ │ ServicioRepo│ │ CarritoRepo   │   │
│   └─────────────┘ └────────────┘ └────────────────┘   │
└───────────────────────────────────────────────────────┘
                         ▲
                         │ 
                         ▼
┌───────────────────────────────────────────────────────┐
│                    Capa de Modelo                     │
│  ┌─────────────┐ ┌────────────┐ ┌───────────────┐     │
│  │ Usuario     │ │ Servicio   │ │ CarritoCompra │     │
│  └─────────────┘ └────────────┘ └───────────────┘     │
└───────────────────────────────────────────────────────┘
                         ▲
                         │ 
                         ▼
┌───────────────────────────────────────────────────────┐
│                  Capa de Persistencia                 │
│                ┌─────────────────────┐                │
│                │     Base de datos   │                │
│                └─────────────────────┘                │
└───────────────────────────────────────────────────────┘
```

Esta implementación sigue estrictamente el patrón arquitectónico MVC donde:

1. **Modelo**: Entidades JPA que representan la estructura de datos
2. **Vista**: Templates Thymeleaf que muestran la interfaz de usuario
3. **Controlador**: Clases Spring que manejan las peticiones HTTP

## 🏗️ Patrones de Diseño Implementados

### Patrones Arquitectónicos

#### 🏛️ MVC (Modelo-Vista-Controlador)

Implementado como patrón arquitectónico principal que estructura toda la aplicación.

```java
// Modelo: Entidad JPA
@Entity
@Table(name = "servicios")
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String nombre;
    private String descripcion;
    private String categoria;
    // ...
}

// Controlador: Maneja peticiones HTTP
@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    private ServicioRepository servicioRepository;
    
    @GetMapping
    public String dashboard(Model model, HttpSession session) {
        // Verificar autenticación
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }
        
        // Obtener servicios y agregarlos al modelo
        List<Servicio> servicios = servicioRepository.findByActivoTrue();
        model.addAttribute("servicios", servicios);
        
        return "dashboard"; // Vista
    }
}

// Vista: Template Thymeleaf (dashboard.html)
// <div th:each="servicio : ${servicios}">
//     <h3 th:text="${servicio.nombre}"></h3>
// </div>
```

**Justificación**: El patrón MVC permite separar claramente la lógica de negocio (Modelo), la presentación (Vista) y la lógica de control (Controlador), facilitando el mantenimiento y la escalabilidad del sistema.

#### 🧱 Capas (Layered Architecture)

Implementado para separar responsabilidades mediante capas bien definidas.

```java
// Capa de Modelo (Entidad)
@Entity
public class Usuario { /* ... */ }

// Capa de Repositorio (Acceso a datos)
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);
}

// Capa de Controlador (Manejo de peticiones)
@Controller
public class AuthController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    // ...
}
```

**Justificación**: La arquitectura por capas facilita la separación de responsabilidades, mejora la cohesión y reduce el acoplamiento entre componentes.

### Patrones de Diseño GoF

#### 📋 Repository

Implementado mediante el uso de Spring Data JPA para el acceso a datos.

```java
@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Integer> {
    List<Servicio> findByActivoTrue();
    List<Servicio> findByCategoria(String categoria);
}
```

**Justificación**: El patrón Repository abstrae la lógica de acceso a datos, proporcionando una interfaz uniforme para operaciones CRUD y consultas personalizadas.

#### 🔄 Factory Method

Implementado en la configuración de Spring para la creación de beans.

```java
@Configuration
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

**Justificación**: El patrón Factory Method permite crear objetos sin especificar la clase concreta, lo que facilita la configuración y el testing.

#### 🧬 State

Implementado en el manejo de estados del carrito de compras.

```java
public enum EstadoCarrito {
    EN_PROCESO,
    COMPRADO,
    EXPIRADO
}

@Controller
public class CarritoController {
    @PostMapping("/procesar-compra")
    public String procesarCompra(HttpSession session, RedirectAttributes redirectAttributes) {
        // Cambio de estado de EN_PROCESO a COMPRADO
        List<CarritoCompra> itemsCarrito = carritoRepository.findByUsuarioIdAndEstado(
                (Integer) session.getAttribute("usuarioId"), 
                EstadoCarrito.EN_PROCESO);
        
        for (CarritoCompra item : itemsCarrito) {
            // Cambio de estado
            item.setEstado(EstadoCarrito.COMPRADO);
            item.setFechaCompra(LocalDateTime.now());
            
            // Cálculo de fecha de expiración según el tipo de plan
            if (item.getTipoPlan().equals("MENSUAL")) {
                item.setFechaExpiracion(LocalDateTime.now().plusMonths(1));
            } else if (item.getTipoPlan().equals("TRIMESTRAL")) {
                item.setFechaExpiracion(LocalDateTime.now().plusMonths(3));
            } else if (item.getTipoPlan().equals("ANUAL")) {
                item.setFechaExpiracion(LocalDateTime.now().plusYears(1));
            }
            
            carritoRepository.save(item);
        }
        
        return "redirect:/mis-compras";
    }
}
```

**Justificación**: El patrón State permite modelar el ciclo de vida de las compras, facilitando la transición entre estados y las acciones asociadas a cada estado.

#### 🛒 Strategy

Implementado en la selección de diferentes planes de suscripción.

```java
// Estrategia implementada dentro del controlador
@PostMapping("/agregar-al-carrito")
public String agregarAlCarrito(@RequestParam Integer servicioId,
                             @RequestParam String tipoPlan,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
    // ...
    
    // Estrategia de cálculo de precio según el plan
    BigDecimal precio;
    switch (tipoPlan) {
        case "MENSUAL":
            precio = servicio.getPrecioMensual();
            break;
        case "TRIMESTRAL":
            precio = servicio.getPrecioTrimestral();
            break;
        case "ANUAL":
            precio = servicio.getPrecioAnual();
            break;
        default:
            precio = BigDecimal.ZERO;
    }
    
    // ...
}
```

**Justificación**: El patrón Strategy permite seleccionar dinámicamente diferentes algoritmos (en este caso, estrategias de precios) según las necesidades, lo que facilita la expansión con nuevos tipos de planes.

#### 💼 Data Transfer Object (DTO)

Implementado para el registro de usuarios.

```java
// DTO para el registro de usuarios
public class RegistroDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    private String email;
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;
    
    // getters y setters
}

@Controller
public class AuthController {
    @PostMapping("/registro")
    public String registro(@Valid @ModelAttribute RegistroDTO registroDTO,
                         BindingResult result,
                         RedirectAttributes redirectAttributes) {
        // Validar y procesar el DTO
        // ...
    }
}
```

**Justificación**: Los DTOs facilitan la transferencia de datos entre subsistemas, especialmente para operaciones que no requieren todas las propiedades de una entidad.

## 🎮 Demostración Visual

### 🔐 Autenticación de Usuarios

![Grabación 2025-05-06 155717](https://github.com/user-attachments/assets/2a14c2d8-bdf0-4cea-b41e-56838a34aff1)

 Proceso completo de registro de nuevos usuarios y el inicio de sesión, implementando el manejo seguro de contraseñas con BCrypt y la gestión de sesiones con HttpSession.

### 🛍️ Catálogo y Detalles de Servicio

![Grabación 2025-05-06 160200](https://github.com/user-attachments/assets/540a53f8-d340-4413-8488-050492c7fdd9)

Demostración de la funcionalidad principal del catálogo, mostrando cómo los usuarios pueden ver detalles completos de cada servicio y el proceso de selección del plan de suscripción antes de agregarlo al carrito, implementando el patrón Strategy para el cálculo de precios según el plan elegido.

## ✨ Características Principales

- 🔐 **Autenticación completa**: Sistema de registro, login y gestión de sesiones implementado con HttpSession y BCrypt para encriptación de contraseñas.
- 🛍️ **Catálogo de servicios**: Visualización de servicios con filtrado por categorías y detalles completos del producto.
- 🛒 **Carrito de compras avanzado**: Gestión completa con diferentes planes de suscripción, prevención de duplicados y cálculo automático de precios.
- 📊 **Sistema de suscripciones**: Manejo de suscripciones con fechas de expiración calculadas según el plan elegido.
- 📱 **Diseño responsive**: Interfaz adaptable a diferentes dispositivos mediante CSS moderno y Bootstrap.
- 🔄 **Gestión de estados**: Implementación de patrones State y Observer para manejar el ciclo de vida de las compras.

## 📊 Modelos de Datos

### Diagrama de Entidades

![image](https://github.com/user-attachments/assets/231177c0-7ed7-455a-8b1e-ad4c33b0aca3)


### Estructura de Entidades

```java
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String nombre;
    
    @Column(unique = true)
    private String email;
    
    private String password;
    
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo = TipoUsuario.CLIENTE;
    
    // getters y setters
}

@Entity
@Table(name = "servicios")
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String nombre;
    private String descripcion;
    private String categoria;
    
    private BigDecimal precioMensual;
    private BigDecimal precioTrimestral;
    private BigDecimal precioAnual;
    
    private boolean activo = true;
    
    // getters y setters
}

@Entity
@Table(name = "carrito_compra", 
       uniqueConstraints = {
           @UniqueConstraint(columnNames = {"usuario_id", "servicio_id", "estado"})
       })
public class CarritoCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "usuario_id")
    private Integer usuarioId;
    
    @Column(name = "servicio_id")
    private Integer servicioId;
    
    private String tipoPlan;
    private BigDecimal precio;
    
    @Enumerated(EnumType.STRING)
    private EstadoCarrito estado = EstadoCarrito.EN_PROCESO;
    
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    private LocalDateTime fechaCompra;
    private LocalDateTime fechaExpiracion;
    
    // getters y setters
}
```

## 🧩 Desglose de Módulos

### Módulo de Autenticación

Este módulo gestiona el registro, inicio de sesión y cierre de sesión de usuarios.

```java
@Controller
public class AuthController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @PostMapping("/registro")
    public String registro(@Valid @ModelAttribute RegistroDTO registroDTO,
                         BindingResult result,
                         RedirectAttributes redirectAttributes) {
        // Lógica de registro
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String email, 
                       @RequestParam String password, 
                       HttpSession session,
                       RedirectAttributes redirectAttributes) {
        // Lógica de inicio de sesión
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Lógica de cierre de sesión
        session.invalidate();
        return "redirect:/";
    }
}
```

### Módulo de Catálogo

Gestiona la visualización y filtrado de servicios disponibles.

```java
@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    private ServicioRepository servicioRepository;
    
    @GetMapping
    public String dashboard(Model model, HttpSession session) {
        // Lógica para mostrar todos los servicios
    }
    
    @GetMapping("/categoria/{categoria}")
    public String filtrarPorCategoria(@PathVariable String categoria,
                                    Model model,
                                    HttpSession session) {
        // Lógica para filtrar por categoría
    }
    
    @GetMapping("/servicio/{id}")
    public String detalleServicio(@PathVariable Integer id,
                                Model model,
                                HttpSession session) {
        // Lógica para mostrar detalles de un servicio
    }
}
```

### Módulo de Carrito

Gestiona el carrito de compras y el proceso de compra.

```java
@Controller
@RequestMapping("/carrito")
public class CarritoController {
    @Autowired
    private CarritoCompraRepository carritoRepository;
    
    @Autowired
    private ServicioRepository servicioRepository;
    
    @PostMapping("/agregar")
    public String agregarAlCarrito(@RequestParam Integer servicioId,
                                 @RequestParam String tipoPlan,
                                 HttpSession session) {
        // Lógica para agregar al carrito
    }
    
    @GetMapping("/ver")
    public String verCarrito(Model model, HttpSession session) {
        // Lógica para ver el carrito
    }
    
    @PostMapping("/eliminar/{id}")
    public String eliminarDelCarrito(@PathVariable Integer id,
                                   HttpSession session) {
        // Lógica para eliminar del carrito
    }
    
    @PostMapping("/procesar-compra")
    public String procesarCompra(HttpSession session) {
        // Lógica para procesar la compra
    }
}
```

## 🛠️ Tecnologías

- **Backend**: Java 17, Spring Boot 3.1.x
- **Frontend**: Thymeleaf, HTML5, CSS3, JavaScript, Bootstrap
- **Persistencia**: JPA/Hibernate, H2/MySQL
- **Servidor**: Apache Tomcat
- **Herramientas de desarrollo**: Maven, Git

## 📦 Instalación

```bash
# Clonar repositorio
git clone https://github.com/usuario/ecommerce-spring.git

# Navegar al directorio
cd ecommerce-spring

# Compilar el proyecto
mvn clean package

# Ejecutar la aplicación
java -jar target/ecommerce-spring-1.0.0.jar

# Alternativamente, desplegar el WAR en Tomcat
cp target/ecommerce-spring-1.0.0.war /path/to/tomcat/webapps/
```

### Configuración de la Base de Datos

```properties
# application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
spring.datasource.username=root
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```
---

```
 _____                                            
|  ___|                                           
| |__ ___ ___  _ __ ___  _ __ ___   ___ _ __ ___ 
|  __/ __/ _ \| '_ ` _ \| '_ ` _ \ / _ \ '__/ __|
| |_| (_| (_) | | | | | | | | | | |  __/ |  \__ \
\____\___\___/|_| |_| |_|_| |_| |_|\___|_|  |___/
                                                  
```

_Desarrollado como proyecto educativo para demostrar patrones de diseño y arquitectura por capas en aplicaciones Spring Boot._
