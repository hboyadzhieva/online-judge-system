sap.ui.define([
	       'sap/ui/core/mvc/Controller',
	       'sap/ui/model/json/JSONModel'
	], function (Controller, JSONModel) {
	'use strict';
	
	var Controller = Controller.extend('summer.camp.judge.controller.Login', {
		onInit: function () {
			var oComponent = this.getOwnerComponent();
			
			this._router = oComponent.getRouter();
		},
		onLoginPress: function () {
			var _this = this,
				email = this.byId('emailId').getValue(),
				password = this.byId('passwordId').getValue();
			
			jQuery.post({
				method: 'POST',
				url: 'api/v1/users/login',
				contentType : "application/json; charset=utf-8",
				data: JSON.stringify({
					email: email,
					password: password
				})
			}).done(function (user) {
				_this._router.navTo('tasks');
			}).fail(function (error) {
				console.log(error);
			});
		}
	});
	
	return Controller;
});