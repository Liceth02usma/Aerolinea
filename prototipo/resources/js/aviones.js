import Helpers from './helpers.js'

export default class Aviones {

    static #action

    static #populateSeats = (value = '', inicio, final, multiplo) => {
        let sillas = []

        for (let i = inicio; i <= final; i += multiplo) {
            sillas.push(i)
        }

        sillas = sillas.map(item => ({ nSilla: item }))
        console.log(sillas)
        const selectSilla = Helpers.populateSelectList(
            value, sillas, 'nSilla', 'nSilla'
        )

        return sillas
    }

    static async init() {
        console.log("esta adentro")
        await Aviones.#loadAll()
        document.querySelector('#aviones-aceptar').addEventListener('click', Aviones.#crud)
        document.querySelector('#aviones-cerrar').addEventListener('click', Aviones.#close)


        Aviones.#populateSeats('#ejecutivas', 4, 40, 4)
        Aviones.#populateSeats('#economicas', 6, 240, 6)
        return this
    }

    /**
     * Crea una tabla con todos los registros del archivo
     */
    static async #loadAll() {
        const infoTable = document.querySelector('#info-aviones')
        try {
            const response = await Helpers.fetchData("http://localhost:4567/sillas/total")
            if (response.message === 'ok') {
                let rows = ''

                response.data.forEach(avion => {
                    rows += `
        <tr>
            <td>${avion.matricula}</td>
            <td>${avion.modelo}</td>
            <td>${avion.totalSillas.economicas}</td>
            <td>${avion.totalSillas.ejecutivas}</td>
        </tr>`
                })

                const htmlTable = `
    <table class="pure-table pure-table-striped">
        <thead>
            <tr>
                <th>MATRICULA</th>
                <th>MODELO</th>
                <th>SILLAS ECONOMICAS</th>
                <th>SILLAS EJECUTIVAS</th>
            </tr>
        </thead>
        <tbody>
            ${rows}
        </tbody>
    </table>`

                infoTable.innerHTML = htmlTable
            } else {
                Helpers.notice(
                    infoTable, 'El servidor no pudo responder a la petición',
                    'danger', response
                )
            }
        } catch (e) {
            Helpers.notice(infoTable, 'No hay acceso al recurso de pasajeros', 'danger', e)
        }
    }

    static async #crud() {
        switch (Aviones.#action) {
            case 'create':
                await Aviones.create()
                break
            case 'retrieve':
                await Aviones.retrieve()
                break
            case 'update':
                await Aviones.update()
                break
            case 'delete':
                await Aviones.delete()
        }
    }

    /**
     * Envía al servidor una solicitud para crear un registro con los datos del formulario
     */
    static async create() {
        if (!Helpers.okForm('#form-aviones')) {
            return
        }

        try {
            let response = await Helpers.fetchData(`http://localhost:4567/aviones`, {
                method: 'POST',
                body: {
                    matricula: document.querySelector('#matricula').value.trim(),
                    modelo: document.querySelector('#modelo').value.trim(),
                }
            })
            if (response.message === 'ok') {

                let response2 = await Helpers.fetchData(`http://localhost:4567/sillas`, {
                    method: 'POST',
                    body: {
                        avion: document.querySelector('#matricula').value.trim(),
                        ejecutivas: document.querySelector('#ejecutivas').value.trim(),
                        economicas: document.querySelector('#economicas').value.trim(),
                    }
                })
                if (response2.message === 'ok') {
                    await Aviones.#loadAll()
                    document.querySelector('ul[id^="list-crud"]').classList.remove('disabled')
                    await Helpers.notice('#form-aviones #state', 'Registro creado')
                    Helpers.toggle('#frm-edit-aviones', 'show', 'hide')
                }
            } else {
                Helpers.notice('#form-aviones #state', response.message, 'danger', response)
            }
        } catch (e) {
            Helpers.notice(
                '#form-aviones #state', 'Sin acceso a la creación de registros', 'danger', e
            )
        }

    }

    /**
     * Enviar al servidor una solicitud de búsqueda de registro y si lo encuentra, mostrar los datos
     */
        static async retrieve() {
            const matricula = document.querySelector('#matricula').value.trim()
    
            try {
                let response = await Helpers.fetchData(
                    `http://localhost:4567/sillas/avion/${matricula}`
                )
    
                if (response.message === 'ok') {
                    document.querySelector('#modelo').value = response.data.modelo
                    document.querySelector('#ejecutivas').value = response.data.totalSillas.ejecutivas
                    document.querySelector('#economicas').value = response.data.totalSillas.economicas
                    Helpers.collapse("#form-aviones > ul li", false, 1, 2);
                } else {
                    Helpers.collapse("#form-aviones > ul li", true, 1, 2);
                    await Helpers.notice(
                        '#form-aviones #state', response.message, 'warning', response
                    )
                }
            } catch (e) {
                await Helpers.notice(
                    '#form-aviones #state', 'Error al intentar buscar', 'danger', e
                )
            }
        }

    /**
     * Envia al servidor una solicitud de actualización con los datos del formulario.
     * Por razones de integridad referencial, no se permite cambiar la identificación
     */
    static async update() {
        if (!Helpers.okForm('#form-aviones')) {
            return
        }
        
        try {
            const matricula = document.querySelector('#matricula').value.trim()
        
            let response = await Helpers.fetchData(
                `http://localhost:4567/aviones/${matricula}`, {
                method: 'PUT',
                body: {
                    matricula: matricula, 
                    modelo: document.querySelector('#modelo').value.trim()
                }
            })
        
            if (response.message === 'ok') {
                await Aviones.#loadAll()
                document.querySelector('ul[id^="list-crud"]').classList.remove('disabled')
                await Helpers.notice('#form-aviones #state', 'Registro actualizado')
                Helpers.toggle('#frm-edit-aviones', 'show', 'hide')
            } else {
                Helpers.notice('#form-aviones #state', response.message, 'danger', response)
            }
        
        } catch (exception) {
            Helpers.notice(
               '#form-aviones #state', 
               'Sin acceso a la actualización de registros de aviones', 
               'danger', 
               exception
            )
        }
    }

    /**
     * Envía al servidor una solicitud de eliminación de eliminación de un registro
     */
     static async delete() {
        const matricula = document.querySelector('#matricula').value.trim()

        try {

            const url = `http://localhost:4567/aviones/matricula=${matricula}`
            const response = await Helpers.fetchData(url, {
                method: 'DELETE'
            })
            if (response.message === 'ok') {

                const url2 = `http://localhost:4567/sillas/remove/${matricula}`
                const response2 = await Helpers.fetchData(url2, {
                method: 'DELETE'
            })
                if (response2.message === 'ok') {
                    await Aviones.#loadAll()
                    Helpers.toggle('#frm-edit-aviones', 'show', 'hide')
                    document.querySelector('ul[id^="list-crud"]').classList.remove('disabled')
                await Helpers.notice('#form-aviones #state', 'Registro eliminado')
            }
                
            } else {
                Helpers.notice(
                    '#form-aviones #state', response.message, 'danger', response
                )
                Helpers.collapse("#form-aviones > ul li", true, 1, 2);
            }
        } catch (e) {
            Helpers.notice(
                '#form-aviones #state', 'Error al intentar eliminar', 'danger', e
            )
        }
    }

    static async #close() {
        // habilitar las opciones del CRUD
        document.querySelector('ul[id^="list-crud"]').classList.remove('disabled')
        // oculta el formulario de entrada de datos
        Helpers.toggle('#frm-edit-aviones', 'show', 'hide')
        // por sospecha quita el posible colapsado de elementos de lista
        Helpers.collapse("#form-aviones > ul li", false, 1, 2);
    }

    static action(_action) {
        if ('retrieve|delete'.includes(_action)) {
            // para retrieve y delete sólo mostrar la entrada de ID
            Helpers.collapse("#form-aviones > ul li", true, 1, 2);
        }

        const matricula = document.querySelector('#matricula').value.trim()
        if (_action === 'update' && matricula === '') {
            Helpers.notice('#list-crud-aviones', 'Busque primero el vuelo a actualizar')
            return
        }
        if(_action === 'update'){
            Helpers.collapse("#form-aviones > ul li", true, 2, 2);
        }
        // mostrar el formulario de entrada de datos
        Helpers.toggle('#frm-edit-aviones', 'hide', 'show')
        // deshabilitar las opciones del CRUD
        document.querySelector('ul[id^="list-crud"]').classList.add('disabled')
        Aviones.#action = _action
    }

}

