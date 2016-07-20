sap.ui.define([
	'sap/ui/core/mvc/Controller'
], function (Controller) {
	'use strict';

	var Controller = Controller.extend('summer.camp.judge.controller.Sidebar', {
		onInit: function () {
			var oComponent = this.getOwnerComponent();
			
			this._router = oComponent.getRouter();
		},
		onTasksPress: function (oEvent) {
			this._router.navTo('tasks');
		}
	});
	
	return Controller;
});