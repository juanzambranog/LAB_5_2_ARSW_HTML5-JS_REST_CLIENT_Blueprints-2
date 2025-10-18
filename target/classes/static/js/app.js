var app = (function () {

    var useMock = false; // <- cambia a false para usar API real
    var api = useMock ? apimock : apiclient;

    var author = "";
    var blueprints = [];
    var currentBlueprint = null; 
    var canvas, ctx;

    var setAuthor = function (newAuthor) {
        author = newAuthor;
    };

    // Actualizar listado de planos por autor
    var updateBlueprints = function (authname) {
        api.getBlueprintsByAuthor(authname, function (data) {
            author = authname;
            blueprints = data.map(function (bp) {
                return { name: bp.name, points: bp.points.length, raw: bp };
            });

            $("#blueprintsTable tbody").empty();
            $("#authorName").text("Author: " + authname);

            blueprints.map(function (bp) {
                $("#blueprintsTable tbody").append(
                    `<tr>
                        <td>${bp.name}</td>
                        <td>${bp.points}</td>
                        <td><button class="btn btn-info btn-sm" onclick="app.openBlueprint('${authname}','${bp.name}')">Open</button></td>
                    </tr>`
                );
            });

            let total = data.map(bp => bp.points.length)
                            .reduce((a,b) => a+b, 0);
            $("#totalPoints").text("Total user points: " + total);
        });
    };


    // Dibujar blueprint en canvas
    var openBlueprint = function (authname, bpname) {
        api.getBlueprintsByNameAndAuthor(authname, bpname, function (bp) {
            currentBlueprint = bp; // <--- Guardamos el actual
            $("#currentBpName").text(bp.name);
            drawBlueprint(bp);
        });
    };
    // save blueprint
    var saveBlueprint = function () {
        if (!currentBlueprint || !author) {
            alert("No blueprint selected or author missing.");
            return;
        }

        // Llamar al PUT del cliente API
        api.updateBlueprint(author, currentBlueprint.name, currentBlueprint, function () {
            updateBlueprints(author);
        });
    };

    // Repintar el blueprint
    var drawBlueprint = function (bp) {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        if (bp.points.length > 0) {
            ctx.beginPath();
            ctx.moveTo(bp.points[0].x, bp.points[0].y);
            for (var i = 1; i < bp.points.length; i++) {
                ctx.lineTo(bp.points[i].x, bp.points[i].y);
            }
            ctx.stroke();
        }
    };

    // Capturar clicks o toques en el canvas
    var addCanvasListener = function () {
        canvas.addEventListener("pointerdown", function (event) {
            if (!currentBlueprint) return; // si no hay blueprint cargado, no hacer nada

            const rect = canvas.getBoundingClientRect();
            const x = event.clientX - rect.left;
            const y = event.clientY - rect.top;

            currentBlueprint.points.push({ x: x, y: y }); // agrega el punto
            drawBlueprint(currentBlueprint); // repinta
        });
    };

    var init = function () {
        canvas = document.getElementById("blueprintCanvas");
        ctx = canvas.getContext("2d");
        addCanvasListener();
        // Escuchar clic del botón Save / Update
        document.getElementById("saveBlueprint").addEventListener("click", saveBlueprint);

    };






    return {
        setAuthor: setAuthor,
        updateBlueprints: updateBlueprints,
        openBlueprint: openBlueprint,
        init: init
    };
})();

// Inicializar canvas al cargar la página
window.onload = app.init;
