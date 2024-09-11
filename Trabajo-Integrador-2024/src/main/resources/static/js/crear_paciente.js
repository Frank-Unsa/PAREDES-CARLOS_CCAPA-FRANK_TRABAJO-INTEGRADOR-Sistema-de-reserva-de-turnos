document.addEventListener('DOMContentLoaded', function () {
    const formulario = document.querySelector('#crearPacienteForm');

    formulario.addEventListener('submit', function (event) {
        event.preventDefault(); // Evita el envío del formulario de forma predeterminada

        // Obtiene los valores del formulario
        const formData = {
            nombre: document.querySelector('#nombre').value,
            apellido: document.querySelector('#apellido').value,
            dni: document.querySelector('#dni').value,
            fechaAlta: document.querySelector('#fechaAlta').value,
            domicilio: {
                calle: document.querySelector('#calle').value,
                numero: document.querySelector('#numero').value,
                localidad: document.querySelector('#localidad').value,
                provincia: document.querySelector('#provincia').value
            }
        };

        // Configura la solicitud fetch
        fetch('/pacientes', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al crear el paciente');
            }
            return response.json();
        })
        .then(data => {
            // Muestra una respuesta exitosa
            document.querySelector('#response').innerHTML = `<div class="alert alert-success">Paciente creado con éxito: ${data.nombre} ${data.apellido}</div>`;
        })
        .catch(error => {
            // Muestra un mensaje de error
            document.querySelector('#response').innerHTML = `<div class="alert alert-danger">Error: ${error.message}</div>`;
        });
    });
});