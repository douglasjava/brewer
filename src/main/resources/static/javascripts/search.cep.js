var Brewer = Brewer || {};

Brewer.SearchCep = (function() {

	function SearchCep() {
		this.inputCep = $('.js-cep-number');
		this.inputLogradouro = $('#logradouro');
		this.inputComplemento = $('#complemento');
		this.comboEstado = $('#estado');
	}

	SearchCep.prototype.iniciar = function() {
		this.inputCep.on('change', onSearchSelect.bind(this));
	}
	
	function onSearchSelect(evento) {
		if(this.inputCep.val()){
			requestService(this);
		}
	}
	
	function requestService(event) {
        $.ajax({
            type: "POST",
            url: "/brewer/cep",
            contentType: "application/json; charset=utf-8",
            data: { cep: event.inputCep.val() },
            dataType: "json",
            success: function(data) { 
            	preencherCamposRetorno(event, data);       	
            },
            error: function(data) { 
            	console.log(data); 
            }
        });
    }
	
	function preencherCamposRetorno(event, data) {
		let options = [];

		event.inputLogradouro.val(data.logradouro);
    	event.inputComplemento.val(data.complemento);
		
    	//options.push('<option value = 4>' + data.estado_info.nome + '</option>');
    	//event.comboEstado.html(options);
	}
	

	return SearchCep;

}());

$(function() {
	var searchCep = new Brewer.SearchCep();
	searchCep.iniciar();
});