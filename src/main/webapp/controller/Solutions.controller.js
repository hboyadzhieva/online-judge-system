sap.ui.define([
	       'sap/ui/core/mvc/Controller',
	       'sap/ui/model/json/JSONModel'
	], function (Controller, JSONModel) {
	'use strict';
	
	var Controller = Controller.extend('summer.camp.judge.controller.Solutions', {
		onInit: function () {
			var oSolutionsModel = new JSONModel('api/v1/solutions');
			
			this.getView().setModel(oSolutionsModel);
		}
	});
	
	return Controller;
});