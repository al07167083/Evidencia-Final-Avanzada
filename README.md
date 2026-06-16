# Calculadora de IMC — Computación Avanzada en Java

Aplicación web desarrollada con **Jakarta EE** bajo el patrón **Modelo-Vista-Controlador (MVC)**, que permite registrar usuarios, calcular su Índice de Masa Corporal (IMC) y dar seguimiento a su histórico. El histórico se consume a través de un **servicio web REST** con representaciones en **JSON y XML**.

Evidencia de la materia LTTI4020 – Computación Avanzada en Java (Universidad Tecmilenio).

## Tecnologías

| Componente | Versión |
|------------|---------|
| Lenguaje | Java 17 |
| Plataforma | Jakarta EE 9.1 |
| Servidor | GlassFish 7.1.0 (perfil completo) |
| Base de datos | MySQL 8.0 |
| Construcción | Maven |
| IDE | Apache NetBeans |

## Arquitectura (MVC)

- **Modelo** (`mx.tecmilenio.imc.modelo`): `Usuario`, `RegistroIMC`, `CalculadoraIMC`, `ConexionBD`, `UsuarioDAO`, `RegistroIMCDAO`.
- **Vista** (`src/main/webapp`): `index.jsp`, `registro.jsp`, `login.jsp`, `calcular.jsp`, `historico.jsp`.
- **Controlador** (`mx.tecmilenio.imc.controlador` y `mx.tecmilenio.imc.rest`): `RegistroServlet`, `LoginServlet`, `IMCServlet`, `LogoutServlet` y el servicio REST `HistoricoRESTService`.

## Cómo ejecutar

1. Crear la base de datos ejecutando el script `database/imc_db.sql` en MySQL.
2. Ajustar, si es necesario, el usuario y contraseña de MySQL en `ConexionBD.java`.
3. Abrir el proyecto en NetBeans y desplegarlo en GlassFish 7.1.0.
4. Acceder a `http://localhost:8080/imc-web/`.

## Reglas de negocio

- El registro pide nombre completo, edad, sexo, estatura, usuario y contraseña **una sola vez**.
- No se aceptan estaturas fuera del rango 1.00–2.50 m ni edades menores a 15 años.
- Para calcular el IMC es **obligatorio iniciar sesión**.
- No se aceptan masas corporales de 0 o negativas.
- El histórico se obtiene desde el servicio REST `/api/historico`.

## Endpoint REST

```
GET /imc-web/api/historico
Accept: application/json   (o application/xml)
```

Devuelve el histórico del usuario en sesión, con controles de hipermedia (HATEOAS). Si no hay sesión, responde `401` con el error en la misma representación solicitada.
