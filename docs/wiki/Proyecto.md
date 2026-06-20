# Proyecto

## Arquitectura
La aplicación sigue el patrón **Modelo-Vista-Controlador (MVC)**, organizado en paquetes:

### Modelo (`mx.tecmilenio.imc.modelo`)
Contiene los datos y la lógica del negocio; no conoce HTTP ni las pantallas.

| Clase | Responsabilidad |
|-------|-----------------|
| `Usuario` | Datos de la persona registrada. |
| `RegistroIMC` | Una medición (peso, IMC, clasificación, fecha). |
| `CalculadoraIMC` | Calcula el IMC y su clasificación OMS. |
| `UsuarioDAO` | Registra usuarios y valida el inicio de sesión. |
| `RegistroIMCDAO` | Guarda mediciones y consulta el histórico. |
| `ConexionBD` | Entrega la conexión a MySQL. |
| `PasswordUtil` | Cifra y verifica contraseñas (SHA-256 + salt). |

### Vista (`src/main/webapp`)
Páginas JSP que solo muestran formularios y resultados: `index.jsp`, `registro.jsp`, `login.jsp`, `calcular.jsp`, `historico.jsp`.

### Controlador (`mx.tecmilenio.imc.controlador` y `mx.tecmilenio.imc.rest`)
| Clase | Responsabilidad |
|-------|-----------------|
| `RegistroServlet` | Valida (estatura 1.0–2.5 m, edad ≥ 15) y registra. |
| `LoginServlet` | Valida credenciales y abre la sesión. |
| `IMCServlet` | Exige sesión, valida masa > 0, calcula y guarda. |
| `LogoutServlet` | Cierra la sesión. |
| `HistoricoRESTService` | Servicio REST del histórico (JSON/XML, HATEOAS). |
| `AppConfig`, `HistoricoResponse`, `Enlace`, `ErrorResponse` | Configuración y respuestas del servicio REST. |

## Servicio REST
```
GET /imc-web/api/historico
Accept: application/json   (o application/xml)
```
Devuelve el histórico del usuario en sesión con controles de hipermedia. Si no hay sesión, responde `401` con el error en la misma representación.

## Base de datos (`imc_db`)
Dos tablas relacionadas uno a muchos por llave foránea:
- **usuario**: `id_usuario` (PK), `nombre_completo`, `nombre_usuario` (UNIQUE), `contrasena`, `edad` (≥15), `sexo`, `estatura` (1.0–2.5).
- **registro_imc**: `id_registro` (PK), `id_usuario` (FK), `peso` (>0), `imc`, `fecha_medicion`.

## Tecnologías
Java 17 · Jakarta EE 9.1 · GlassFish 7.1.0 · MySQL 8.0 · Maven · Apache NetBeans.

## Manejo del repositorio
- `master`: versión estable, etiquetada con **v1.0.0**.
- `develop`: integra el desarrollo; conserva todos los commits.
- Ramas por recurso: `feature/usuario`, `feature/imc`, `feature/historico-rest`.
- Cada rama de recurso se integró a `develop` con `merge --no-ff`, sin borrar la rama de origen.
