sap.ui.define([
	       'sap/ui/core/mvc/Controller',
	       'sap/ui/model/json/JSONModel'
	], function (Controller, JSONModel) {
	'use strict';
	
	var Controller = Controller.extend('summer.camp.judge.controller.Task', {
		_routePatternMatched: function (oEvent) {
			var sId = oEvent.getParameter('arguments').id;
			
			this.getView().setModel(new JSONModel('api/v1/tasks/' + sId));
		},
		onInit: function () {
			var oComponent = this.getOwnerComponent();
			
			this._router = oComponent.getRouter();
			this._router.getRoute('task').attachPatternMatched(this._routePatternMatched, this);
		},
		onSubmitPress: function (oEvent) {
			var _this = this,
				sLanguage = this.byId('languageSelect').getSelectedItem().getKey(),
				sSolution = this.byId('solutionTextArea').getValue(),
				oTaskModel, oTask;
			
			oTaskModel = this.getView().getModel();
			oTask = oTaskModel.getProperty('/');
			
			jQuery.ajax({
				method: 'POST',
				url: 'api/v1/solutions/evaluate',
				contentType : "application/json; charset=utf-8",
				data: JSON.stringify({
					language: sLanguage,
					text: sSolution,
					task: oTask
				})
			}).done(function (response) {
				_this._router.navTo('solution', {id: response.id});
			}).fail(function (error) {
				console.log(error);
			});
		}
	});
	
	return Controller;
});