//@author hcadavid

apimock=(function(){

	var mockdata=[];

	mockdata["johnconnor"]=	[{author:"johnconnor","points":[{"x":150,"y":120},{"x":215,"y":115}],"name":"house"},
	 {author:"johnconnor","points":[{"x":340,"y":240},{"x":15,"y":215}],"name":"gear"}];
	mockdata["maryweyland"]=[{author:"maryweyland","points":[{"x":140,"y":140},{"x":115,"y":115}],"name":"house2"},
	 {author:"maryweyland","points":[{"x":140,"y":140},{"x":115,"y":115}],"name":"gear2"}];


	mockdata["juan"] = [
		{
			author: "juan",
			name: "estrella",
			points: [
				{x: 250, y: 50},
				{x: 300, y: 200},
				{x: 400, y: 200},
				{x: 320, y: 280},
				{x: 350, y: 400},
				{x: 250, y: 330},
				{x: 150, y: 400},
				{x: 180, y: 280},
				{x: 100, y: 200},
				{x: 200, y: 200},
				{x: 250, y: 50},
			]
		},
		{
			author: "juan",
			name: "cuadrado",
			points: [
				{x: 100, y: 100},
				{x: 400, y: 100},
				{x: 400, y: 400},
				{x: 100, y: 400},
				{x: 100, y: 100}
			]
		}
	];
	


	return {
		getBlueprintsByAuthor:function(authname,callback){
			callback(
				mockdata[authname]
			);
		},

		getBlueprintsByNameAndAuthor:function(authname,bpname,callback){

			callback(
				mockdata[authname].find(function(e){return e.name===bpname})
			);
		}
	}	

})();

/*
Example of use:
var fun=function(list){
	console.info(list);
}

apimock.getBlueprintsByAuthor("johnconnor",fun);
apimock.getBlueprintsByNameAndAuthor("johnconnor","house",fun);*/