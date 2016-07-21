sap.ui.define([
	       'sap/ui/core/mvc/Controller',
	       'sap/ui/model/json/JSONModel'
	], function (Controller, JSONModel) {
	'use strict';
	
	var Controller = Controller.extend('summer.camp.judge.controller.Solution', {
		_routePatternMatched: function (oEvent) {
			var _this = this,
				sId = oEvent.getParameter('arguments').id,
				oSolutionModel = new JSONModel('api/v1/solutions/' + sId);
			
			this.getView().setModel(oSolutionModel);
			oSolutionModel.attachRequestCompleted(function () {
				console.log(oSolutionModel.getProperty('/'));
			});
		},
		onInit: function () {
			var oComponent = this.getOwnerComponent();
			
			this._router = oComponent.getRouter();
			this._router.getRoute('solution').attachPatternMatched(this._routePatternMatched, this);
		}
	});
	
	return Controller;
});