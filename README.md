# 🏥 Sistema de Reserva de Turnos - Clínica Odontológica 🦷  

📌 **Trabajo Integrador**  
Este sistema permite administrar la reserva de turnos en una clínica odontológica, facilitando la gestión de pacientes, odontólogos y citas médicas.  

## 📋 Funcionalidades  

✅ **Administración de Odontólogos**  
- 📄 Listar  
- ➕ Agregar  
- ✏️ Modificar  
- 🗑️ Eliminar  
- 📌 Datos requeridos: *Apellido, Nombre y Matrícula.*  

✅ **Administración de Pacientes**  
- 📄 Listar  
- ➕ Agregar  
- ✏️ Modificar  
- 🗑️ Eliminar  
- 📌 Datos requeridos: *Nombre, Apellido, Domicilio, DNI y Fecha de Alta.*  

✅ **Gestión de Turnos**  
- 📅 Asignar un turno a un paciente con un odontólogo en una fecha y hora determinada.  

✅ **Sistema de Login 🔐**  
- 👤 Validación de usuario con **Usuario** y **Contraseña**.  
- 🎭 **Roles:**  
  - 🏥 `ROLE_ADMIN`: Puede gestionar odontólogos y pacientes.  
  - 👥 `ROLE_USER`: Puede registrar turnos.  
- 📌 Un usuario puede tener un solo rol, asignado directamente en la base de datos.  

---

## 🛠️ Requerimientos Técnicos  

🚀 **Arquitectura en Capas**  
1. 🏗 **Capa de Entidades de Negocio:** Modelado con Java y POO.  
2. 🗄 **Capa de Acceso a Datos (Repository):** Gestión de base de datos.  
3. 🗃 **Capa de Datos:** Base de datos H2 (práctica y liviana).  
4. 🔧 **Capa de Negocio:** Servicios que desacoplan acceso a datos de la vista.  
5. 🎨 **Capa de Presentación:**  
   - 🌐 HTML + JavaScript  
   - 🚀 Framework **Spring Boot MVC**  

⚠️ **Manejo de Excepciones:** Se loguearán todas las excepciones para una mejor depuración.  
🧪 **Tests Unitarios:** Implementados para garantizar la calidad del desarrollo.  

---

## 🖥️ Tecnologías Utilizadas  

🔹 **Java** ☕  
🔹 **Spring Boot** 🌱  
🔹 **Spring Security** 🔐  
🔹 **JPA/Hibernate** 🗄  
🔹 **Base de datos H2** 🗃  
🔹 **HTML + JavaScript** 🌐  

---

## 📷 Capturas de Pantalla  
> 🖼️ Agrega aquí imágenes del sistema en acción 📸  

---

## 📌 Instalación y Ejecución  

1️⃣ Clonar el repositorio:  
```sh
git clone https://github.com/Frank-Unsa/PAREDES-CARLOS_CCAPA-FRANK_TRABAJO-INTEGRADOR-Sistema-de-reserva-de-turnos.git
