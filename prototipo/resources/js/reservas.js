import Helpers from './helpers.js'
import { DateTime, Duration } from "./luxon.min.js"

export default class Reservas {

    static #licor
    static #menu
    static #action
    static #count
    static #sillaElegidaR = []
    static #sillasObjectR
    static #retrieveDelete = true
    static #listaActualVuelos
    static #vueloRegreso2
    static #OriginalistVueloRegreso
    static #sillasObject
    static #sillaElegida = []
    static #mouseover = false
    static #mouseout = true
    static #vueloElegido
    static #pasajeroLista
    static #listadoSillasIda
    static #silla
    static #listadoSillasRegreso

    static async init() {
        await Reservas.#loadAll();//se carga la lista de reservas 
        document.querySelector("#reservas-aceptar").addEventListener("click", Reservas.#crud);
        document.querySelector("#reservas-cerrar").addEventListener("click", Reservas.#close);
        document.querySelector('#ida-regreso').addEventListener('change', Reservas.#regreso)
        document.querySelector('#total-pasajeros').addEventListener('change', Reservas.#entradasPasajeros)
        Helpers.collapse('#form-reservas > ul li', true, 2, 2)//como se va a colapsar se envia un true, para el boton d eidea y regreso , para que solo muestre la ida
        Reservas.#count = 1 //aparezca predeterminada un pasajero
        Reservas.#ingresoPasajeros(Reservas.#count)
        await Reservas.#cargarPasajero()
        await Reservas.#cargarVuelos()
        return this;
    }

    /**
     * Crea una tabla con todos los registros del archivo
     */
    static async #loadAll() {
        const infoTable = document.querySelector('#info-reservas')
        try {
            const response = await Helpers.fetchData("http://localhost:4567/reservas")
            const response2 = await Helpers.fetchData("http://localhost:4567/vuelos-reservas")
            if (response.message === 'ok' && response2.message === 'ok') {
                let rows2 = ''
                let htmlTable2 = ''

                response.data.forEach(reserva => {
                    console.log(reserva)
                    let cancelado = (reserva.cancelada) ? "- (Reserva cancelada)" : ""
                    rows2 = `
                    <tr>
                    <td> ${reserva.pasajero.identifiacion} - ${reserva.pasajero.nombre} ${reserva.pasajero.apellido} ${cancelado} 
                    `
                    let rows = ''
                    response2.data.forEach(reservaVuelo => {
                        let identificacion = reservaVuelo.reserva.pasajero.identifiacion
                        let identificacion2 = reserva.pasajero.identifiacion
                        let fechaHora = reserva.fechaHoraSinStr
                        let fechaHora2 = reservaVuelo.reserva.fechaHoraSinStr
                        if (identificacion == identificacion2 && fechaHora == fechaHora2) {
                            let fechaHora = DateTime.fromISO(reservaVuelo.vuelo.fechaHora).toFormat('yyyy-MM-dd hh:mm a')

                            rows += `
                    
                        <tr>
                            <td> ${fechaHora} </td>
                            <td> ${reservaVuelo.vuelo.trayecto.origen} - ${reservaVuelo.vuelo.trayecto.destino} </td>
                            <td> ${reservaVuelo.vuelo.avion.matricula} </td>
                            <td> ${reservaVuelo.silla.posicion} </td>
                            <td> ${Helpers.format(reservaVuelo.vuelo.trayecto.costo)} </td>
                            
                        </tr>
                    `
                        }
                    })
                    htmlTable2 += `
                        ${rows2}
                    <table class="pure-table pure-table-striped">
                            ${rows} 
                    </table>
                </td>
            </tr>
        <br>
                    `
                })

                const htmlTable = `
                <table>
                <tbody>
                    ${htmlTable2} 
                </tbody>
            </table>
            `

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
        switch (Reservas.#action) {
            case 'create':
                await Reservas.create()
                break
            case 'retrieve':
                await Reservas.retrieve()
                break
            case 'update':
                await Reservas.update()
                break
            case 'delete':
                await Reservas.delete()
        }
    }

    static async #MenuLicorToString(menu, licor) {
        let menuLicor = []
        switch (menu) {
            case "0":
                menuLicor.push("INDEFINIDO")
                break;
            case "1":
                menuLicor.push("POLLO_A_LA_NARANJA")
                break;
            case "2":
                menuLicor.push("VEGETARIANO")
                break;
            case "3":
                menuLicor.push("FILETE_DE_PESCADO")
                break;
        }

        switch (licor) {
            case "0":
                menuLicor.push("WHISKEY")
                break;
            case "1":
                menuLicor.push("OPORTO")
                break;
            case "2":
                menuLicor.push("VINO")
                break;
            case "3":
                menuLicor.push("NINGUNO")
                break;
        }
        Reservas.#menu = menuLicor[0]
        Reservas.#licor = menuLicor[1]
    }

    /**
     * Envía al servidor una solicitud para crear un registro con los datos del formulario
     */
    static async create() {
        // posible algoritmo digo yo, yo no se xd 
        ///si la opcion ida regreso esta activa entonces pide tales campos
        // numero de iteraciones == al formulario
        //jeraraquia cantidad de pasajeros por consiguiente ida y regreso
        if (!Helpers.okForm('#form-reservas')) {
            return
        }
        try {
            console.log(Reservas.#vueloRegreso2)

            let cont = document.querySelector('#total-pasajeros').value
            Reservas.#MenuLicorToString(document.querySelector('#opciones-menu').value, document.querySelector('#opciones-licor').value)
            for (let i = 1; i <= cont; i++) {
                let pasajeroId = (document.querySelector(`#pasajero-${i}`).value).split("-")[0]
                let vuelo = Reservas.#vueloElegido
                let vuelo2 = Reservas.#vueloRegreso2
                let sillaF = document.querySelector(`#ida-${i}`).value.substring(0, 2)
                let sillaC = document.querySelector(`#ida-${i}`).value.substring(2, 3)
                let sillaF2 = document.querySelector(`#regreso-${i}`).value.substring(0, 2)
                let sillaC2 = document.querySelector(`#regreso-${i}`).value.substring(2, 3)
                let response = await Helpers.fetchData(`http://localhost:4567/reservas`, {
                    method: 'POST',
                    body: {
                        fechaHora: document.querySelector('#fechaHora').value,
                        pasajero: pasajeroId
                    }
                })
                if (response.message === 'ok') {
                    let response2
                    if (!(Reservas.#sillaElegida[i - 1].hasOwnProperty('menu'))) {
                        response2 = await Helpers.fetchData(`http://localhost:4567/vuelos-reservas`, {
                            method: 'POST',
                            body: {
                                "fechaHoraReserva": document.querySelector('#fechaHora').value,
                                "pasajero": pasajeroId,
                                "fechaHoraVuelo": vuelo.fechaHora,
                                "origen": vuelo.trayecto.origen,
                                "destino": vuelo.trayecto.destino,
                                "avion": vuelo.avion.matricula,
                                "fila": sillaF,
                                "columna": sillaC
                            }
                        })
                    } else {
                        response2 = await Helpers.fetchData(`http://localhost:4567/vuelos-reservas`, {
                            method: 'POST',
                            body: {
                                "fechaHoraReserva": document.querySelector('#fechaHora').value,
                                "pasajero": pasajeroId,
                                "fechaHoraVuelo": vuelo.fechaHora,
                                "origen": vuelo.trayecto.origen,
                                "destino": vuelo.trayecto.destino,
                                "avion": vuelo.avion.matricula,
                                "fila": sillaF,
                                "columna": sillaC,
                                "menu": Reservas.#menu,
                                "licor": Reservas.#licor,
                            }
                        })
                    }
                    if (document.querySelector('#ida-regreso').checked) {
                        let response3
                        if (!(Reservas.#sillaElegida[i - 1].hasOwnProperty('menu'))) {
                            response3 = await Helpers.fetchData(`http://localhost:4567/vuelos-reservas`, {
                                method: 'POST',
                                body: {
                                    "fechaHoraReserva": document.querySelector('#fechaHora').value,
                                    "pasajero": pasajeroId,
                                    "fechaHoraVuelo": vuelo2.fechaHora,
                                    "origen": vuelo2.trayecto.origen,
                                    "destino": vuelo2.trayecto.destino,
                                    "avion": vuelo2.avion.matricula,
                                    "fila": sillaF2,
                                    "columna": sillaC2
                                }
                            })
                        } else {
                            response3 = await Helpers.fetchData(`http://localhost:4567/vuelos-reservas`, {
                                method: 'POST',
                                body: {
                                    "fechaHoraReserva": document.querySelector('#fechaHora').value,
                                    "pasajero": pasajeroId,
                                    "fechaHoraVuelo": vuelo2.fechaHora,
                                    "origen": vuelo2.trayecto.origen,
                                    "destino": vuelo2.trayecto.destino,
                                    "avion": vuelo2.avion.matricula,
                                    "fila": sillaF2,
                                    "columna": sillaC2,
                                    "menu": Reservas.#menu,
                                    "licor": Reservas.#licor,
                                }
                            })
                        }
                    }
                    if (response2.message === 'ok') {
                        await Reservas.#loadAll()
                        document.querySelector('ul[id^="list-crud"]').classList.remove('disabled')
                        await Helpers.notice('#form-reservas #state', 'Registro creado')
                        Helpers.toggle('#frm-edit-reservas', 'show', 'hide')
                    }
                } else {
                    Helpers.notice('#form-reservas #state', response.message, 'danger', response)
                }

            }
        } catch (e) {
            Helpers.notice(
                '#form-reservas #state', 'Sin acceso a la creación de registros', 'danger', e
            )
        }

    }

    static #regreso(e) {
        Helpers.collapse('#form-reservas > ul li', !e.target.checked, 2, 2)
        const regresos = document.querySelectorAll('#form-reservas > ul> li div[id ^="divregreso"]')
        regresos.forEach(div => div.hidden = e.target.checked ? false : true)
    }
    static #entradasPasajeros(e) {
        const contador = e.target.value
        if (contador > Reservas.#count) {
            const total = contador - Reservas.#count
            for (let i = 0; i < total; i++) {
                Reservas.#ingresoPasajeros(contador)
            }
            Reservas.#count = contador
        }
        if (contador < Reservas.#count) {
            let lista = document.querySelectorAll('#form-reservas > ul> li')
            const total = Reservas.#count - contador
            for (let i = 0; i < total; i++) {
                lista[lista.length - 4].remove();
            }
            Reservas.#count -= total
        }
    }
    static async #ingresoPasajeros(contador) {
        const regreso = document.querySelector('#ida-regreso').checked
        const htmlRegreso = regreso ? "" : 'hidden'
        const html = `<li id="item-${contador}">
    <div class="field-divided3">
        <div class="">
            Pasajeros
            <select id="pasajero-${contador}" name="pasajero-${contador}" class="field-select">
            <option value="y">Elija el pasajero</option>
            </select>
        </div>
        &nbsp;&nbsp;
        <div class="">
            Ida
            <select id="ida-${contador}" name="ida-${contador}" class="field-select">
            <option value="y">01A</option>
            </select>
        </div>
        &nbsp;&nbsp;
        <div id="divregreso-${contador}" class="" ${htmlRegreso}>
            Regreso
            <select id="regreso-${contador}" name="regreso-${contador}" class="field-select">
                <option value="y">02B</option>
            </select>
        </div>
    </div>
</li>`
        document.querySelector('#executive-options').insertAdjacentHTML('beforebegin', html)
        const eventPasajero2 = Helpers.populateSelectList(`#pasajero-${contador}`, Reservas.#pasajeroLista, "formato", "formato") //llenar la lista
        eventPasajero2.addEventListener('change', e => {
            Reservas.#pasajeroLista = Reservas.#pasajeroLista.filter(el => el.formato != e.target.value)
            console.log(Reservas.#pasajeroLista)
        })

        const actual = Reservas.#listadoSillasIda
        const eventSillaIda2 = Helpers.populateSelectList(`#ida-${contador}`, Reservas.#listadoSillasIda, "silla", "silla")
        eventSillaIda2.addEventListener('change', e => {
            const indice = document.querySelector(`#ida-${contador}`).value
            Reservas.#sillasObject.forEach(silla => {
                silla.forEach(silla2 => {
                    if (silla2.posicion === indice) {
                        Reservas.#sillaElegida.push(silla2)

                    }
                })
            })
            Reservas.#listadoSillasIda = Reservas.#listadoSillasIda.filter(el => el.silla != e.target.value)
        })
        const eventSillaRegreso2 = Helpers.populateSelectList(`#regreso-${contador}`, Reservas.#listadoSillasRegreso, "silla", "silla")
        eventSillaRegreso2.addEventListener('change', e => {
            const indice = document.querySelector(`#ida-${contador}`).value
            Reservas.#sillasObject.forEach(silla => {
                silla.forEach(silla2 => {
                    if (silla2.posicion === indice) {
                        Reservas.#sillaElegidaR.push(silla2)

                    }
                })
            })
            Reservas.#listadoSillasRegreso = Reservas.#listadoSillasRegreso.filter(el => el.silla != e.target.value)
        })

        //obtener la silla aprtir del link    switch ejecutiva true o false un if si es ejecutiva se muestra 
        //sino se tapa

        document.querySelector(`#ida-${contador}`).addEventListener('mouseover', Reservas.#sillas)
        document.querySelector(`#ida-${contador}`).addEventListener('mouseout', Reservas.#sillas) //añadir los eventos a los botones
        document.querySelector(`#regreso-${contador}`).addEventListener('mouseover', Reservas.#sillas)
        document.querySelector(`#regreso-${contador}`).addEventListener('mouseover', Reservas.#sillas)

        //falta completar
    }
    static #sillas(e) {
        if (e.type === 'mouseover') {
            document.querySelector('#opciones-menu').disabled = Reservas.#mouseover//disabled es desactivado y al decirle false es que no lo esta
            document.querySelector('#opciones-licor').disabled = Reservas.#mouseover
        }
        if (e.type === 'mouseout') {
            document.querySelector('#opciones-menu').disabled = Reservas.#mouseout
            document.querySelector('#opciones-licor').disabled = Reservas.#mouseout
        }
    }
    //``  cuando se va a poner algo de valor
    static async #cargarVuelos() {
        try {
            const vuelos = await Helpers.fetchData(`http://localhost:4567/vuelos`)//espera q ue todo cargue para poder seguir await
            //recorrer el vuelo para mostrarlo
            vuelos.data.forEach(vuelo => {
                const fechaHora = DateTime.fromISO(vuelo.fechaHora).toFormat("yyyy-MM-dd hh:mm a")
                const costo = Helpers.format(vuelo.trayecto.costo)
                vuelo.formato = `${vuelo.trayecto.origen}-${vuelo.trayecto.destino}, ${fechaHora}, $${costo}`
            })
            Reservas.#listaActualVuelos = vuelos.data
            console.log(Reservas.#listaActualVuelos)
            console.log("adentro de cargar vuelos")
            const vuelosIda = Helpers.populateSelectList("#vuelo-ida", vuelos.data, "formato", "formato")
            vuelosIda.addEventListener('change', async (e) => {
                console.log("adentro de funcion")
                await Reservas.#sillaIda()
                await Reservas.#vueloRegreso()
            });
        } catch (error) {
            console.log(error)
        }
    }
    static async #vueloRegreso() {
        console.log("adentro :) que no funcione esto es distinto")
        //crear una ruta solo por origen y destino, para devolver todos los trayectos que tienen ese origen y destino, que sean inversos
        const indice = document.querySelector('#vuelo-ida').selectedIndex
        const vuelos = await Helpers.fetchData(`http://localhost:4567/vuelos`)//espera q ue todo cargue para poder seguir await
        //recorrer el vuelo para mostrarlo
        const vuelo = vuelos.data[indice]//se obtiene el vuelo
        //verificar, si todos no estan inversos, no siempre va a funcionar

        const url = `http://localhost:4567/vuelos/vueloIda/${vuelo.trayecto.destino}&${vuelo.trayecto.origen}`
        const vuelosRegreso2 = await Helpers.fetchData(url)
        console.log(Object.keys(vuelosRegreso2.data).length)

        vuelosRegreso2.data.forEach(vuelo => {
            const fechaHora = DateTime.fromISO(vuelo.fechaHora).toFormat("yyyy-MM-dd hh:mm a")
            const costo = Helpers.format(vuelo.trayecto.costo)
            vuelo.formato = `${vuelo.trayecto.origen}-${vuelo.trayecto.destino}, ${fechaHora}, $${costo}`
        })

        Reservas.#OriginalistVueloRegreso = vuelosRegreso2.data
        console.log(Reservas.#OriginalistVueloRegreso)
        let regresoVuelo
        if (Object.keys(vuelosRegreso2.data).length != 0) {
            regresoVuelo = Helpers.populateSelectList("#vuelo-regreso", vuelosRegreso2.data, "formato", "formato")
            let list = document.querySelector("#vuelo-regreso")
            list.add(new Option(`Se encontraron ${Object.keys(vuelosRegreso2.data).length}`))
        } else {
            let list = document.querySelector("#vuelo-regreso")
            list.options.length = 0 // limpia la lista
            list.add(new Option("No hay vuelos disponibles regrese en bus ;("))
            let list2 = document.querySelector("#regreso-1")
            list2.options.length = 0 // limpia la lista
            list2.add(new Option("No hay vuelos ahora mucho menos sillas ;("))
        }
        console.log("estoy afuera...:.")
        regresoVuelo.addEventListener("change", async (e) => {
            console.log("estoy adentro...:.")
            await Reservas.#sillasRegreso(vuelosRegreso2)
            console.log("otra vez")
        })
    }
    //crear una funcion para cargar las sillas segun ese vuelo, resiva el avion del vuelo y busque las isllas de ese vuelo 

    static async #sillasRegreso(vuelos) {
        const indice = document.querySelector('#vuelo-regreso').selectedIndex
        const vueloE = vuelos.data[indice]
        Reservas.#vueloRegreso2 = vueloE
        console.log(vueloE)
        let url = `http://localhost:4567/sillas/disponibles/fechaHora=${vueloE.fechaHora}&origen=${vueloE.trayecto.origen}&destino=${vueloE.trayecto.destino}&avion=${vueloE.avion.matricula}`
        const sillas = await Helpers.fetchData(url)
        let listado = []
        console.log(sillas.data)
        sillas.data.forEach(silla => {
            silla.forEach(silla2 => {
                listado.push(silla2.posicion)
            })
        })
        Reservas.#sillasObjectR = sillas.data
        listado = listado.map(item => ({ silla: item }))
        Reservas.#listadoSillasRegreso = listado
        const eventSillaRegreso = Helpers.populateSelectList("#regreso-1", listado, "silla", "silla")
        eventSillaRegreso.addEventListener('change', e => {
            const indice2 = document.querySelector('#ida-1').selectedIndex
            const silla = sillas.data[0][indice2]
            Reservas.#sillaElegidaR.length = 0
            Reservas.#sillaElegidaR.push(silla)

            if (silla.hasOwnProperty('menu')) {
                Reservas.#mouseout = false
                Reservas.#mouseover = false
                Reservas.#sillas(e)
            } else {
                Reservas.#mouseout = true
                Reservas.#mouseover = true
                Reservas.#sillas(e)
            }
            Reservas.#listadoSillasRegreso = listado.filter(el => el.silla != e.target.value)
        })
    }

    static async #sillaIda() {//implementar, RETORNAR UN JSON CON TODAS ESAS SILLAS DISPONIBLES EN ESE VUELO
        const indice = document.querySelector('#vuelo-ida').selectedIndex
        const vuelos = await Helpers.fetchData(`http://localhost:4567/vuelos`)//espera q ue todo cargue para poder seguir await
        const vuelo = vuelos.data[indice]//se obtiene el vuelo
        Reservas.#vueloElegido = vuelo
        const sillas = await Helpers.fetchData(`http://localhost:4567/sillas/disponibles/fechaHora=${vuelo.fechaHora}&origen=${vuelo.trayecto.origen}&destino=${vuelo.trayecto.destino}&avion=${vuelo.avion.matricula}`)
        let listado = []
        sillas.data.forEach(silla => {
            silla.forEach(silla2 => {
                listado.push(silla2.posicion)
            })
        })
        Reservas.#sillasObject = sillas.data
        listado = listado.map(item => ({ silla: item }))
        Reservas.#listadoSillasIda = listado
        if (Reservas.#retrieveDelete) {
            const eventSillaIda = Helpers.populateSelectList("#ida-1", listado, "silla", "silla")
            eventSillaIda.addEventListener('change', e => {
                const indice2 = document.querySelector('#ida-1').selectedIndex
                const silla = sillas.data[0][indice2]
                Reservas.#sillaElegida.length = 0
                Reservas.#sillaElegida.push(silla)
                if (silla.hasOwnProperty('menu')) {
                    Reservas.#mouseout = false
                    Reservas.#mouseover = false
                    Reservas.#sillas(e)
                } else {
                    Reservas.#mouseout = true
                    Reservas.#mouseover = true
                    Reservas.#sillas(e)
                }
                Reservas.#listadoSillasIda = listado.filter(el => el.silla != e.target.value)
            })
        }else{
            const sillas2 = await Helpers.fetchData(`http://localhost:4567/sillas`)
            let lista = []
            sillas2.data.forEach(silla => {
                if(silla.avion.matricula === vuelo.avion.matricula){
                    lista.push(silla.posicion)
                }
            })
            lista = lista.map(item => ({ silla: item }))
            const eventSillaR = Helpers.populateSelectList("#ida-1", lista, "silla", "silla")
        }

    }
    static async #cargarPasajero() {
        try {
            const pasajeros = await Helpers.fetchData(`http://localhost:4567/pasajeros`)//espera q ue todo cargue para poder seguir await
            //recorrer el vuelo para mostrarlo
            pasajeros.data.forEach(pasajero => {

                pasajero.formato = `${pasajero.identifiacion}-${pasajero.nombre}, ${pasajero.apellido}`
            })
            Reservas.#pasajeroLista = pasajeros



            const eventPasajero = Helpers.populateSelectList(`#pasajero-1`, pasajeros.data, "formato", "formato")
            eventPasajero.addEventListener('change', e => {
                Reservas.#pasajeroLista = pasajeros.data.filter(el => el.formato != e.target.value)
            })
            //segun el pasajero que se escoja se añaden  mas partes
        } catch (error) {
            console.log(error)
        }
    }

    /**
     * Enviar al servidor una solicitud de búsqueda de registro y si lo encuentra, mostrar los datos
     */
    static async retrieve() {

        let pasajeroId = (document.querySelector(`#pasajero-1`).value).split("-")[0]
        let fechaHora = document.querySelector('#fechaHora').value
        let vuelo = Reservas.#vueloElegido
        let sillaF = document.querySelector(`#ida-1`).value.substring(0, 2)
        let sillaC = document.querySelector(`#ida-1`).value.substring(2, 3)
        try {
            let response = await Helpers.fetchData(
                `http://localhost:4567/vuelos-reservas/fechaHoraReserva=${fechaHora}&pasajero=${pasajeroId}&fechaHoraVuelo=${vuelo.fechaHora}&origen=${vuelo.trayecto.origen}&destino=${vuelo.trayecto.destino}&avion=${vuelo.avion.matricula}&fila=${sillaF}&columna=${sillaC}`
            )
            let vuelo2 = response.data.vuelo
            const fechaHora = DateTime.fromISO(vuelo2.fechaHora).toFormat("yyyy-MM-dd hh:mm a")
            let pasajero = response.data.reserva.pasajero
            const costo = Helpers.format(vuelo2.trayecto.costo)
            let vuelo3 = `${vuelo2.trayecto.origen}-${vuelo2.trayecto.destino}, ${fechaHora}, $${costo}`

            if (response.message === 'ok') {
                document.querySelector('#fechaHora').value = response.data.vuelo.fechaHora
                document.querySelector('#vuelo-ida').value = vuelo3
                document.querySelector('#ida-1').value = response.data.silla.posicion
                document.querySelector('#pasajero-1').value = `${pasajero.identifiacion}-${pasajero.nombre}, ${pasajero.apellido}`
            } else {
                await Helpers.notice(
                    '#form-aviones #state', response.message, 'warning', response
                )
            }
        } catch (e) {
            await Helpers.notice(
                '#form-reservas #state', 'Error al intentar buscar', 'danger', e
            )
        }
    }

    /**
     * Envia al servidor una solicitud de actualización con los datos del formulario.
     * Por razones de integridad referencial, no se permite cambiar la identificación
     */
    static async update() {
        Helpers.notice('#form-pasajeros > ul #state', 'Pasajeros.update() pendiente de implementar')
    }

    /**
     * Envía al servidor una solicitud de eliminación de eliminación de un registro
     */
    static async delete() {
        Helpers.notice('#form-aviones > ul #state', 'Aviones.delete() pendiente de implementar')
    }

    static async #close() {
        // habilitar las opciones del CRUD
        document.querySelector('ul[id^="list-crud"]').classList.remove('disabled')
        // oculta el formulario de entrada de datos
        Helpers.toggle('#frm-edit-reservas', 'show', 'hide')
        // por sospecha quita el posible colapsado de elementos de lista
        Helpers.collapse("#form-reservas > ul li", false, 1, 2);
    }

    static action(_action) {
        if ('retrieve|delete'.includes(_action)) {
            Reservas.#retrieveDelete = false

            Helpers.collapse("#form-reservas > ul li", true, 0, 2);
            // para retrieve y delete sólo mostrar la entrada de ID
            //--------- IDA REGRESO ----------
            // Helpers.collapse("#ida-regreso", true, 0, 0)
            // Helpers.collapse("#label-ida-regreso", true, 0, 0)

            // //--------- TOTAL PASAJERO ----------
            // Helpers.collapse("#total-pasajeros", true, 0, 0)
            // Helpers.collapse("#label-total-pasajeros", true, 0, 0)

            // //------------ MENU ------------
            // Helpers.collapse("#opciones-menu", true, 0, 0)
            // Helpers.collapse("#label-opciones-menu", true, 0, 0)

            // //------------ LICOR ------------
            // Helpers.collapse("#opciones-licor", true, 0, 0)
            // Helpers.collapse("#label-opciones-licor", true, 0, 0)
        }

        // mostrar el formulario de entrada de datos
        Helpers.toggle('#frm-edit-reservas', 'hide', 'show')
        // deshabilitar las opciones del CRUD
        document.querySelector('ul[id^="list-crud"]').classList.add('disabled')
        Reservas.#action = _action

        //solucion ortodoxa ponerlo colapsar y despues quitarle el colapsar
    }

}

