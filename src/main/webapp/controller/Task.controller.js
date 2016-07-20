sap.ui.define([
	       'sap/ui/core/mvc/Controller',
	       'sap/ui/model/json/JSONModel'
	], function (Controller, JSONModel) {
	'use strict';
	
	var Controller = Controller.extend('summer.camp.judge.controller.Task', {
		_routePatternMatched: function (oEvent) {
			var sId = oEvent.getParameter('arguments').id,
				oTasksModel, aTasks, oTask;
			
			oTasksModel = this.getView().getModel('Tasks');
			aTasks = oTasksModel.getProperty('/');
			
			for (var i = 0; i < aTasks.length; i++) {
				if (aTasks[i].id == sId) {
					oTask = aTasks[i];
					break;
				}
			}
			
			this.getView().setModel(new JSONModel(oTask));
		},
		onInit: function () {
			var oComponent = this.getOwnerComponent();
			
			this._router = oComponent.getRouter();
			this._router.getRoute('task').attachPatternMatched(this._routePatternMatched, this);
		},
		onSubmitPress: function (oEvent) {
			var oTaskModel, oTask;
			
			oTaskModel = this.getView().getModel();
			oTask = oTaskModel.getProperty('/');
			
			jQuery.ajax({
				method: 'POST',
				url: '',
				contentType : "application/json; charset=utf-8",
				data: JSON.stringify({
					
				})
			});
		}
	});
	
	return Controller;
});