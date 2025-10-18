
apiclient = (function () {

    // Obtener planos por autor
    var getBlueprintsByAuthor = function (authname, callback) {
        $.get("http://localhost:8080/blueprints/"+ authname, function (data) {
            callback(data);
        }).fail(function () {
            console.error("Error getting blueprints for author: " + authname);
            callback([]);
        });
    };

    // Obtener un plano específico por autor y nombre
    var getBlueprintsByNameAndAuthor = function (authname, bpname, callback) {
        $.get("http://localhost:8080/blueprints/" + authname + "/" + bpname, function (data) {
            callback(data);
        }).fail(function () {
            console.error("Error getting blueprint " + bpname + " for author: " + authname);
            callback(null);
        });
    };

    // actualizar (PUT) un plano existente
    var updateBlueprint = function (authname, bpname, blueprint, callback) {
        $.ajax({
            url: "http://localhost:8080/blueprints/" + authname + "/" + bpname,
            type: "PUT",
            data: JSON.stringify(blueprint),
            contentType: "application/json"
        }).done(function () {
            console.log("Blueprint updated successfully");
            callback();
        }).fail(function () {
            console.error("Error updating blueprint " + bpname);
        });
    };

    return {
        getBlueprintsByAuthor: getBlueprintsByAuthor,
        getBlueprintsByNameAndAuthor: getBlueprintsByNameAndAuthor,
        updateBlueprint: updateBlueprint

    };

})();
