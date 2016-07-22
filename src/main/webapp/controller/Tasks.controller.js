sap.ui.define([
	       'sap/ui/core/mvc/Controller'
	], function (Controller) {
	'use strict';
	
	var Controller = Controller.extend('summer.camp.judge.controller.Tasks', {
		
		 login : function() {

				var username;
				$.ajax({
				    url : "api/v1/userAuth/getUserInfo",
				    type : "GET",
				    dataType : "json",
				    async : false,
				}).success(function(userData) {
				    username = userData["firstName"] + " " + userData["lastName"];

				}).fail(function(data) {
				    alert("Authentication failed")
				});

				return username;
			    }
		
	});
	
	return Controller;
});