# Guías

## Requisitos previos
- Apache NetBeans
- GlassFish 7.1.0
- JDK 17
- MySQL 8.0
- Maven

## 1. Preparar la base de datos
1. Inicia el servidor MySQL.
2. Ejecuta el script `database/imc_db.sql`. Esto crea la base `imc_db` con las tablas `usuario` y `registro_imc`.
3. Si tu usuario/contraseña de MySQL no son `root/root`, ajústalos en `mx/tecmilenio/imc/modelo/ConexionBD.java`.

## 2. Abrir y ejecutar el proyecto
1. Abre el proyecto Maven en NetBeans (`Open Project`).
2. Verifica que GlassFish 7.1.0 esté registrado como servidor.
3. Ejecuta el proyecto (`Run`). NetBeans compila, genera `imc-web.war` y lo despliega.
4. Abre en el navegador: `http://localhost:8080/imc-web/`.

## 3. Usar la aplicación
1. **Registro:** crea una cuenta con nombre, edad (≥15), sexo, estatura (1.0–2.5 m), usuario y contraseña.
2. **Inicio de sesión:** ingresa con tu usuario y contraseña. Sin iniciar sesión no es posible calcular.
3. **Cálculo:** captura tu masa corporal (mayor a 0). Se muestra el IMC y su clasificación.
4. **Histórico:** consulta el IMC actual, la gráfica de tendencia y la tabla de mediciones (datos obtenidos del servicio REST).

## 4. Probar el servicio REST
Con una sesión iniciada, en el navegador o con curl:
```
GET http://localhost:8080/imc-web/api/historico
Accept: application/json    -> respuesta en JSON
Accept: application/xml     -> respuesta en XML
```

## 5. Flujo de trabajo con Git
```bash
# Clonar
git clone <url-del-repositorio>

# Ver ramas y el historial
git branch -a
git log --all --graph --oneline --decorate

# La versión estable está etiquetada
git checkout v1.0.0
```
