import Helpers from './helpers.js'
import { DateTime, Duration } from "./luxon.min.js"


export default class Vuelos {

    static #action
    static #trayectos // referencia la respuesta dada por el servidor
    static #listTrayectos // referencia al seleccionable de trayectos
    static #aviones // referencia la respuesta dada por el servidor
    static #listAviones // referencia al seleccionable de trayectos

    static async init() {
        console.log("esta adentro")
        this.#trayectos = await Helpers.fetchData("http://localhost:4567/trayectos")
        this.#trayectos.data.forEach(t => t.toString = `${t.origen} - ${t.destino}`)

        this.#listTrayectos = Helpers.populateSelectList(
            '#trayectos', this.#trayectos.data, 'toString', 'toString'
         )
         this.#aviones = await Helpers.fetchData("http://localhost:4567/aviones")
         this.#aviones.data.forEach(t => t.toString = `${t.matricula}`)
         this.#listAviones = Helpers.populateSelectList(
            '#aviones', this.#aviones.data, 'matricula', 'toString'
         )
         
         console.log(this.#listTrayectos)
        await Vuelos.#loadAll()
        document.querySelector('#vuelos-aceptar').addEventListener('click', Vuelos.#crud)
        document.querySelector('#vuelos-cerrar').addEventListener('click', Vuelos.#close)
        return this
    }

    /**
     * Crea una tabla con todos los registros del archivo
     */
    static async #loadAll() {
        const infoTable = document.querySelector('#info-vuelos')
        try {
            const response = await Helpers.fetchData("http://localhost:4567/vuelos")
            if (response.message === 'ok') {
                let rows = ''

                response.data.forEach(vuelo => {
                   let fechaHora = DateTime.fromISO(vuelo.fechaHora).toFormat('yyyy-MM-dd hh:mm a')
                    let duracion = Duration.fromISO(vuelo.trayecto.duracion).toFormat('hh:mm')
                    rows += `
        <tr>
            <td id = "td2">${fechaHora}</td>
            <td>${vuelo.trayecto.origen}</td>
            <td>${vuelo.trayecto.destino}</td>
            <td>${duracion}</td>
            <td>${Helpers.format(vuelo.trayecto.costo)}</td>
            <td>${vuelo.avion.matricula}</td>
            <td>${vuelo.cancelado2}</td>
        </tr>`
                })

                const htmlTable = `
    <table class="pure-table pure-table-striped">
        <thead>
            <tr>
                <th id = "td2">FECHA DEL VUELO</th>
                <th>ORIGEN</th>
                <th>DESTINO</th>
                <th>DURACION</th>
                <th>COSTO</th>
                <th>MATRICULA</th>
                <th>CANCELADO</th>
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
        switch (Vuelos.#action) {
            case 'create':
                await Vuelos.create()
                break
            case 'retrieve':
                await Vuelos.retrieve()
                break
            case 'update':
                await Vuelos.update()
                break
            case 'delete':
                await Vuelos.delete()
        }
    }

    /**
     * Envía al servidor una solicitud para crear un registro con los datos del formulario
     */
     static async create() {
        if (!Helpers.okForm('#form-vuelos')) {
            return
        }
        const trayecto = this.#trayectos.data[this.#listTrayectos.selectedIndex]

        try {
            let response = await Helpers.fetchData(`http://localhost:4567/vuelos`, {
                method: 'POST',
                body: {
                    fechaHora: document.querySelector('#fechaHora').value,
                    origen: trayecto.origen,
                    destino: trayecto.destino,
                    avion: this.#listAviones.value, // value tiene asignada la matrícula
                    cancelado: document.querySelector('#cancelado').checked
                
                }
            })
            if (response.message === 'ok') {
                await Vuelos.#loadAll()
                document.querySelector('ul[id^="list-crud"]').classList.remove('disabled')
                await Helpers.notice('#form-vuelos #state', 'Registro creado')
                Helpers.toggle('#frm-edit-vuelos', 'show', 'hide')
            } else {
                //Helpers.notice('#form-vuelos #state', response.message, 'danger', response)
            }
        } catch (e) {
            //Helpers.notice(
             //   '#form-vuelos #state', 'Sin acceso a la creación de registros', 'danger', e
            //)
        }
    }

    /**
     * Enviar al servidor una solicitud de búsqueda de registro y si lo encuentra, mostrar los datos
     */
     static async retrieve() {
        const trayecto = document.querySelector('#trayectos').value.trim()
        var t =trayecto.split(" - ")
        const origen = t[0]
        const destino = t[1]
        const fechaHora = (document.querySelector('#fechaHora').value.trim())
        const matricula = document.querySelector('#aviones').value.trim()
        

        try {
            let response = await Helpers.fetchData(
                `http://localhost:4567/vuelos/fechaHora=${fechaHora}&origen=${origen}&destino=${destino}&avion=${matricula}`
            )
            console.log(response.message)
            if (response.message === 'ok') {
                document.querySelector('#cancelado').checked = response.data.cancelado
                Helpers.collapse("#form-vuelos > ul li", false, 3, 3);
            } else {
                Helpers.collapse("#form-vuelos > ul li", true, 3, 3);
                await Helpers.notice(
                    '#form-vuelos #state', response.message, 'warning', response
                )
            }
        } catch (e) {
            //await Helpers.notice(
             //   '#form-vuelos #state', 'Error al intentar buscar', 'danger', e
            //)
        }
    }

    /**
     * Envia al servidor una solicitud de actualización con los datos del formulario.
     * Por razones de integridad referencial, no se permite cambiar la identificación
     */
    static async update() {
        if (!Helpers.okForm('#form-vuelos')) {
            return
        }
        
        let fechaHora = document.querySelector('#fechaHora').value
        const trayecto = document.querySelector('#trayectos').value
        var t =trayecto.split(" - ")
        const origen = t[0]
        const destino = t[1]

        // se cambia el formato de la duración por el utilizado en Java 
        console.log(fechaHora + origen + destino)
        try {
            let response = await Helpers.fetchData(`http://localhost:4567/vuelos`, {
                method: 'PUT',
                body: {
                    fechaHora,
                    origen,
                    destino,
                    avion: document.querySelector('#aviones').value,
                    cancelado: document.querySelector('#cancelado').checked
                }
            })
            
            if (response.message === 'ok') {
                await Vuelos.#loadAll()
                document.querySelector('ul[id^="list-crud"]').classList.remove('disabled')
                await Helpers.notice('#form-vuelos #state', 'Registro actualizado')
                Helpers.toggle('#frm-edit-vuelos', 'show', 'hide')
            } else {
               // Helpers.notice('#form-vuelos #state', response.message, 'danger', response)
            }
        } catch (exception) {
            console.log(exception)
            //Helpers.notice(
             //  '#form-vuelos #state', 
             //  'Sin acceso a la creación de registros', 
             //  'danger', 
              // exception)
        }
    }

    /**
     * Envía al servidor una solicitud de eliminación de eliminación de un registro
     */
        static async delete() {
            const trayecto = document.querySelector('#trayectos').value.trim()
        var t =trayecto.split(" - ")
       const origen = t[0]
       const destino = t[1]
        const fechaHora = (document.querySelector('#fechaHora').value.trim())
        const matricula = document.querySelector('#aviones').value.trim()
        console.log(destino + origen +fechaHora + matricula)
    
            try {
                const url = `http://localhost:4567/vuelos/fechaHora=${fechaHora}&origen=${origen}&destino=${destino}&avion=${matricula}`
                console.log(url)
                const response = await Helpers.fetchData(url, {
                    method: 'DELETE'
                })
    
                if (response.message === 'ok') {
                    await Vuelos.#loadAll()
                    document.querySelector('ul[id^="list-crud"]').classList.remove('disabled')
                    Helpers.toggle('#frm-edit-vuelos', 'show', 'hide')
                    await Helpers.notice('#form-vuelos #state', 'Registro eliminado')
                } else {
                    //Helpers.notice(
                        //'#form-vuelos #state', response.message, 'danger', response
                    //)
                }
            } catch (e) {
               // Helpers.notice(
                    //'#form-vuelos #state', 'Error al intentar eliminar', 'danger', e
                //)
            }
        }

    static async #close() {
        // habilitar las opciones del CRUD
        document.querySelector('ul[id^="list-crud"]').classList.remove('disabled')
        // oculta el formulario de entrada de datos
        Helpers.toggle('#frm-edit-vuelos', 'show', 'hide')
        // por sospecha quita el posible colapsado de elementos de lista
        Helpers.collapse("#form-vuelos > ul li", false, 1, 2);
    }

    static action(_action) {
        if ('retrieve|delete'.includes(_action)) {
            // para retrieve y delete sólo mostrar la entrada de ID
            Helpers.collapse("#form-vuelos > ul li", true, 3, 3);
        }
        // mostrar el formulario de entrada de datos
        Helpers.toggle('#frm-edit-vuelos', 'hide', 'show')
        // deshabilitar las opciones del CRUD
        document.querySelector('ul[id^="list-crud"]').classList.add('disabled')
        Vuelos.#action = _action
    }

}

