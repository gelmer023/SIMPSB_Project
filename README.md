# 💇 SIMPSB - Sistema de Información para Peluquería

![Estado](https://img.shields.io/badge/Estado-Desarrollo-yellow)
![Versión](https://img.shields.io/badge/Versión-1.0-blue)
![Licencia](https://img.shields.io/badge/Licencia-SENA-green)

## 📋 Descripción del Proyecto

**SIMPSB** es un **Sistema de Información para la gestión de una peluquería** desarrollado en el **SENA** (Servicio Nacional de Aprendizaje). 

Este proyecto permite:
- ✅ Gestión de clientes y citas
- ✅ Control de empleados y horarios
- ✅ Registro de servicios y facturas
- ✅ Sistema de pagos y comisiones
- ✅ Reportes y calificaciones
- ✅ Autenticación de usuarios con roles (Cliente, Empleado, Supervisor)

---

## 🛠️ Tecnologías y Lenguajes

| Componente | Tecnología | Versión |
|-----------|-----------|---------|
| **Lenguaje Backend** | Java | 8+ |
| **Framework Web** | JavaServer Faces (JSF) | 2.2+ |
| **Servidor de Aplicaciones** | GlassFish | 5.1+ |
| **Base de Datos** | MySQL / Oracle | 5.7+ / 11g+ |
| **ORM** | JPA (Java Persistence API) | 2.0+ |
| **Build Tool** | Apache Ant | 1.10+ |
| **Frontend** | XHTML, CSS, Bootstrap, JavaScript | - |
| **Reportes** | JasperReports | 5.5+ |

---

## 📦 Requisitos Previos

Antes de comenzar, asegúrate de tener instalado:

### **Software Requerido**
1. **Java Development Kit (JDK)** 8 o superior
   - [Descargar JDK](https://www.oracle.com/java/technologies/downloads/)
   - Verificar: `java -version`

2. **Apache Ant**
   - [Descargar Ant](https://ant.apache.org/bindownload.cgi)
   - Verificar: `ant -version`

3. **GlassFish Server** 5.1 o superior
   - [Descargar GlassFish](https://javaee.github.io/glassfish/)
   - O usarlo desde NetBeans

4. **MySQL o Oracle Database**
   - [MySQL](https://www.mysql.com/downloads/)
   - O [Oracle Database](https://www.oracle.com/database/technologies/xe-downloads.html)

5. **NetBeans IDE** (recomendado)
   - [Descargar NetBeans](https://netbeans.apache.org/download/index.html)
   - O usar Eclipse / IntelliJ IDEA

6. **MySQL Workbench** (opcional, para gestionar BD)
   - [Descargar MySQL Workbench](https://www.mysql.com/products/workbench/)

---

## 🚀 Instalación y Configuración

### **Paso 1: Clonar o Descargar el Proyecto**

```bash
# Opción A: Clonar desde Git (si está disponible)
git clone https://github.com/tu-usuario/SIMPSB.git
cd SIMPSB

# Opción B: Descargar manualmente desde GitHub y extraer
# Luego navegar a la carpeta
cd C:\Users\music\OneDrive\Documentos\GitHub\SIMPSB1
```

### **Paso 2: Descargar la Base de Datos**

1. **Crear la base de datos MySQL**
   - Abrir MySQL Workbench o línea de comandos
   - Ejecutar el script SQL proporcionado:

```bash
# Desde terminal MySQL
mysql -u root -p < SIMPSB_Database.sql

# O manualmente en MySQL Workbench:
# 1. File → Open SQL Script
# 2. Seleccionar el archivo SQL
# 3. Click en Lightning (ejecutar)
```

2. **Si no tienes el script SQL:**
   - Contactar al equipo de desarrollo del SENA
   - Pedir el archivo `SIMPSB_Database.sql` o `SIMPSB_DB.sql`

### **Paso 3: Configurar la Conexión a la Base de Datos**

**En el archivo `src/conf/persistence.xml`:**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.1" xmlns="http://xmlns.jcp.org/xml/ns/persistence"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence 
   http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd">
  
  <persistence-unit name="SIMPSB_PU" transaction-type="JTA">
    <jta-data-source>jdbc/SIMPSB</jta-data-source>
    <exclude-unlisted-classes>false</exclude-unlisted-classes>
    <properties>
      <property name="javax.persistence.schema-generation.database.action" value="create"/>
      <property name="eclipselink.logging.level" value="FINE"/>
    </properties>
  </persistence-unit>
</persistence>
```

### **Paso 4: Crear Fuente de Datos en GlassFish**

En **NetBeans**:
1. Click derecho en GlassFish Server
2. Selecciona "Open Server Admin Console"
3. Usuario: `admin` | Contraseña: `adminadmin` (por defecto)
4. Navega a: **Resources** → **JDBC** → **Connection Pools**
5. Click en **New**
   - **Name**: `SIMPSB_Pool`
   - **Resource Type**: `javax.sql.DataSource`
   - **Database Vendor**: `mysql`
6. Click **Next** y completa:
   - **Server Name**: `localhost`
   - **Port**: `3306`
   - **Database Name**: `SIMPSB`
   - **User**: `root`
   - **Password**: `tu_password`
7. Click **Finish**

8. Ahora ve a: **Resources** → **JDBC** → **JDBC Resources**
9. Click en **New**
   - **JNDI Name**: `jdbc/SIMPSB`
   - **Pool Name**: `SIMPSB_Pool`
10. Click **OK**

**O usando línea de comandos:**

```bash
# Crear Connection Pool
asadmin create-jdbc-connection-pool \
  --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource \
  --restype javax.sql.DataSource \
  --property user=root:password=tu_password:URL=jdbc:mysql://localhost:3306/SIMPSB \
  SIMPSB_Pool

# Crear JDBC Resource
asadmin create-jdbc-resource \
  --connectionpoolid SIMPSB_Pool \
  jdbc/SIMPSB
```

### **Paso 5: Descargar Driver MySQL (si es necesario)**

Si GlassFish no tiene el driver MySQL:

1. [Descargar MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/)
2. Copiar `mysql-connector-java-x.x.x.jar` a:
   ```
   GlassFish_Installation/glassfish/domains/domain1/lib/
   ```
3. Reiniciar GlassFish

### **Paso 6: Compilar el Proyecto**

**Opción A: Con NetBeans**
```
1. Abrir el proyecto en NetBeans
2. Click derecho → "Clean and Build"
3. Esperar a que termine
```

**Opción B: Con terminal (Ant)**
```bash
cd C:\Users\music\OneDrive\Documentos\GitHub\SIMPSB1
ant clean build
```

### **Paso 7: Desplegar en GlassFish**

**Opción A: Con NetBeans**
```
1. Click derecho en SIMPSB1 → "Deploy"
2. Esperar mensaje "BUILD SUCCESSFUL"
3. Aplicación lista en http://localhost:8080/SIMPSB1
```

**Opción B: Con asadmin**
```bash
asadmin deploy --force dist/SIMPSB.war
```

---

## 🌐 Acceder a la Aplicación

Después del despliegue, abre tu navegador y ve a:

```
http://localhost:8080/SIMPSB1/
```

### **Credenciales por Defecto** (según tu BD):
- **Usuario**: admin
- **Contraseña**: admin123

> ⚠️ **Importante**: Cambia las credenciales en producción

---

## 👥 Roles de Usuarios

El sistema tiene 3 roles principales:

| Rol | Funcionalidades |
|-----|-----------------|
| **Cliente** | Agendar citas, ver perfil, ver historial de servicios |
| **Empleado** | Ver citas asignadas, registrar servicios, actualizar horas |
| **Supervisor** | Gestionar empleados, citas, reportes, facturas, pagos |

---

## 📁 Estructura del Proyecto

```
SIMPSB1/
├── src/
│   ├── java/simpsb/
│   │   ├── controller/           # Controladores (JSF Backing Beans)
│   │   │   ├── SesionController.java
│   │   │   ├── UsuarioController.java
│   │   │   ├── CitasController.java
│   │   │   └── ...
│   │   ├── dao/                  # Acceso a datos (DAOs/Facades)
│   │   │   ├── AbstractFacade.java
│   │   │   ├── UsuarioFacade.java
│   │   │   └── ...
│   │   ├── entidades/            # Entidades JPA
│   │   │   ├── Usuario.java
│   │   │   ├── Cliente.java
│   │   │   └── ...
│   │   └── utils/                # Clases de utilidad
│   │       ├── AppConstants.java
│   │       ├── SessionUtil.java
│   │       ├── ValidationUtil.java
│   │       └── ExceptionUtil.java
│   └── conf/
│       ├── persistence.xml       # Configuración JPA
│       └── MANIFEST.MF
├── web/
│   ├── index.xhtml               # Página de login
│   ├── app/
│   │   ├── login.xhtml
│   │   ├── Cliente/              # Vistas para clientes
│   │   ├── Empleado/             # Vistas para empleados
│   │   └── Supervisor/           # Vistas para supervisores
│   ├── resources/
│   │   ├── css/                  # Estilos CSS
│   │   ├── js/                   # JavaScript personalizado
│   │   └── img/                  # Imágenes
│   ├── FotosPerfil/              # Fotos de perfil de usuarios
│   ├── reportes/                 # Plantillas JasperReports (.jrxml)
│   └── WEB-INF/
│       ├── faces-config.xml      # Configuración JSF
│       ├── web.xml               # Configuración web
│       └── glassfish-resources.xml
├── nbproject/                    # Configuración NetBeans
├── build.xml                     # Script de build (Ant)
└── MEJORAS_IMPLEMENTADAS.md     # Documento de mejoras de código
```

---

## 🗄️ Base de Datos - Tabla Principal

```sql
-- Estructura básica de la BD
CREATE DATABASE SIMPSB;
USE SIMPSB;

-- Tabla de Usuarios (usuarios del sistema)
CREATE TABLE usuarios (
  id_usuario INT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(100) NOT NULL,
  apellido VARCHAR(100) NOT NULL,
  email VARCHAR(150) UNIQUE NOT NULL,
  telefono VARCHAR(20),
  num_documento INT UNIQUE NOT NULL,
  contrasena VARCHAR(255) NOT NULL,
  foto VARCHAR(255),
  id_rol INT NOT NULL,
  FOREIGN KEY (id_rol) REFERENCES roles(id_rol)
);

-- Tabla de Clientes
CREATE TABLE clientes (
  id_cliente INT PRIMARY KEY AUTO_INCREMENT,
  id_usuario INT NOT NULL UNIQUE,
  fecha_registro DATETIME,
  FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

-- Tabla de Citas
CREATE TABLE citas (
  id_cita INT PRIMARY KEY AUTO_INCREMENT,
  id_cliente INT NOT NULL,
  id_empleado INT NOT NULL,
  fecha_cita DATE NOT NULL,
  hora_cita TIME NOT NULL,
  id_estado INT NOT NULL,
  FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente),
  FOREIGN KEY (id_empleado) REFERENCES empleados(id_empleado),
  FOREIGN KEY (id_estado) REFERENCES estados(id_estado)
);

-- Más tablas...
```

---

## 📊 Aplicaciones Recomendadas

### **Para Desarrollo**
- **NetBeans IDE** - Editor y entorno de desarrollo
- **MySQL Workbench** - Gestión de base de datos
- **Postman** - Testing de APIs (si las hay)

### **Para Ejecución**
- **GlassFish Server** - Servidor de aplicaciones (incluido en NetBeans)
- **MySQL Server** - Base de datos
- **Navegador Web** - Chrome, Firefox, Edge

### **Herramientas Adicionales**
- **Git** - Control de versiones
- **Apache Ant** - Build automation
- **HeidiSQL** - Alternativa MySQL Workbench

---

## 🔧 Uso de la Aplicación

### **Para Clientes**

1. **Registrarse**: Click en "Registro" en la página de inicio
2. **Iniciar Sesión**: Ingresar credenciales
3. **Agendar Cita**: Ir a "Mis Citas" → "Agendar Nueva Cita"
4. **Ver Historial**: Consultar citas anteriores y servicios
5. **Editar Perfil**: Actualizar datos personales y foto

### **Para Empleados**

1. **Ver Citas**: Dashboard muestra citas del día
2. **Registrar Servicios**: Marcar servicios realizados
3. **Ver Horarios**: Consultar turno asignado
4. **Actualizar Horas**: Registrar horas trabajadas

### **Para Supervisores**

1. **Gestionar Usuarios**: Crear, editar, eliminar clientes/empleados
2. **Ver Reportes**: Gráficos de citas, pagos, desempeño
3. **Facturación**: Generar facturas de servicios
4. **Pagos**: Procesar pagos y comisiones
5. **Calificaciones**: Ver y gestionar calificaciones de empleados

---

## 🌍 La Web - ¿Dónde se Ve?

La aplicación es **web-based**, se accede a través del navegador:

```
┌─────────────────────────────────┐
│  Cliente Abre Navegador         │
│  http://localhost:8080/SIMPSB1/ │
│                                 │
│  ↓                              │
│                                 │
│  GlassFish recibe petición      │
│  JSF procesa la lógica          │
│  BD retorna datos               │
│                                 │
│  ↓                              │
│                                 │
│  Navegador muestra página       │
│  HTML/CSS/JavaScript            │
└─────────────────────────────────┘
```

### **Pantallas Principales**

- **Login** (`http://localhost:8080/SIMPSB1/`)
- **Registro** (`http://localhost:8080/SIMPSB1/app/Index/registro.xhtml`)
- **Dashboard Cliente** (`http://localhost:8080/SIMPSB1/app/Cliente/indexCliente.xhtml`)
- **Dashboard Empleado** (`http://localhost:8080/SIMPSB1/app/Empleado/indexEmpleado.xhtml`)
- **Dashboard Supervisor** (`http://localhost:8080/SIMPSB1/app/Supervisor/indexSupervisor.xhtml`)

---

## 🔌 Conexión a Base de Datos - Pasos Detallados

### **Paso 1: Verificar que MySQL esté corriendo**

```bash
# En Windows
net start MySQL80

# Verificar que está corriendo
mysql -u root -p -e "SELECT 1;"
```

### **Paso 2: Crear la base de datos**

```bash
# Conectarse a MySQL
mysql -u root -p

# Dentro de MySQL:
CREATE DATABASE SIMPSB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE SIMPSB;

# Importar script SQL
SOURCE C:/ruta/al/SIMPSB_Database.sql;

# Verificar tablas
SHOW TABLES;
```

### **Paso 3: Configurar GlassFish (via NetBeans)**

```
1. Iniciar NetBeans
2. Window → Services
3. Expandir "GlassFish Server"
4. Click derecho → "Start"
5. Expandir "JDBC Drivers"
6. Click derecho en "MySQL (Connector/J)" → "Connect using"
7. Ingresar:
   - Host: localhost
   - Port: 3306
   - Database: SIMPSB
   - User: root
   - Password: [tu contraseña]
8. Click OK
```

### **Paso 4: Crear Fuente de Datos**

En **Admin Console de GlassFish** (`http://localhost:4848`):

```
1. Ir a Resources → JDBC → Connection Pools
2. New:
   Name: SIMPSB_Pool
   Resource Type: javax.sql.DataSource
   Database Vendor: MySQL
3. Next y llenar:
   Server Name: localhost
   Port: 3306
   Database Name: SIMPSB
   User: root
   Password: [contraseña]
4. Test Connection → debe salir "Ping Succeeded"
5. Click Finish

6. Ir a Resources → JDBC → JDBC Resources
7. New:
   JNDI Name: jdbc/SIMPSB
   Pool Name: SIMPSB_Pool
8. Click OK
```

### **Paso 5: Verificar Conexión en la App**

Al desplegar, el proyecto debería conectarse automáticamente usando `jdbc/SIMPSB`.

Para verificar en logs:
```
En NetBeans:
Window → Output → GlassFish Server 5.1

Buscar mensajes como:
"INFO: Connection pool jdbc/SIMPSB created successfully"
```

---

## ⚠️ Problemas Comunes

### **Problema 1: "Connection refused" a la BD**
```
Solución:
1. Verificar que MySQL esté corriendo: net start MySQL80
2. Verificar usuario/contraseña en persistence.xml
3. Verificar que puerto 3306 esté correcto
```

### **Problema 2: "No Connection Pool"**
```
Solución:
1. En Admin Console, crear Connection Pool
2. Reiniciar GlassFish
3. Redeploy la aplicación
```

### **Problema 3: Error al compilar "Package not found"**
```
Solución:
1. Click derecho en proyecto → Clean and Build
2. Si persiste: Delete build folder y recompile
3. Verificar que JDK esté bien configurado
```

### **Problema 4: Aplicación no aparece en browser**
```
Solución:
1. Verificar que GlassFish esté corriendo
2. Revisar logs en Output
3. Verificar puerto: http://localhost:8080/SIMPSB1/
4. Si cambió puerto, modificar URL según corresponda
```

---

## 📝 Archivos de Configuración Importantes

### **persistence.xml**
Define la conexión a la base de datos JPA.

```xml
<!-- Ubicación: src/conf/persistence.xml -->
<!-- Editarlo si cambias el nombre de BD o fuente de datos -->
```

### **faces-config.xml**
Configuración de JSF.

```xml
<!-- Ubicación: web/WEB-INF/faces-config.xml -->
<!-- Define páginas, navegación, beans managed -->
```

### **web.xml**
Configuración de la aplicación web.

```xml
<!-- Ubicación: web/WEB-INF/web.xml -->
<!-- Define servlets, filtros, listeners -->
```

---

## 🎓 Información del Proyecto

- **Institución**: SENA (Servicio Nacional de Aprendizaje)
- **Tipo**: Sistema de Información Web
- **Dominio**: Gestión de Peluquería
- **Versión**: 1.0
- **Estado**: En Desarrollo

---

## 📞 Soporte

Para problemas:
1. Revisar logs en GlassFish Server
2. Verificar conexión a BD
3. Contactar al equipo de desarrollo del SENA
4. Consultar documentación de JEE y JSF

---

## 📚 Recursos Útiles

- [Oracle Java EE Documentation](https://javaee.github.io/)
- [GlassFish Documentation](https://javaee.github.io/glassfish/docs)
- [JSF Official Guide](https://javaserverfaces.github.io/)
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [JPA/Hibernate Guide](https://hibernate.org/orm/documentation/)

---

**Última actualización**: 2026-07-22  
**Versión del README**: 1.0
