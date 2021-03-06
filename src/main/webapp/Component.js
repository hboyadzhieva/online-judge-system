sap.ui.define([
	'sap/ui/core/UIComponent',
	'sap/m/routing/Router',
	'sap/ui/model/resource/ResourceModel',
	'sap/ui/model/json/JSONModel'
], function (UIComponent,
			Router,
			ResourceModel,
			JSONModel) {

	return UIComponent.extend("summer.camp.judge.Component", {
		metadata: {
			routing: {
				config: {
					routerClass: Router,
					viewType: "XML",
					viewPath: "summer.camp.judge.view",
					controlId: "splitApp",
					transition: "slide",
					bypassed: {
						target: ["sidebarView"]
					}
				},
				routes: [
					{
						pattern: "tasks",
						name: "tasks",
						target: ["sidebarView", "tasksView"]
					},
					{
						pattern: "tasks/{id}",
						name: "task",
						target: ["sidebarView", "taskView"]
					},
					{
						pattern: "solution/{id}",
						name: "solution",
						target: ["sidebarView", "solutionView"]
					},
					{
						pattern: "solutions",
						name: "solutions",
						target: ["sidebarView", "solutionsView"]
					}
				],
				targets: {
					taskView: {
						viewName: "Task",
						viewLevel: 3,
						controlAggregation: "detailPages"
					},
					tasksView: {
						viewName: "Tasks",
						viewLevel: 2,
						controlAggregation: "detailPages"
					},
					solutionView: {
						viewName: "Solution",
						viewLevel: 3,
						controlAggregation: "detailPages"
					},
					solutionsView: {
						viewName: "Solutions",
						viewLevel: 3,
						controlAggregation: "detailPages"
					},
					sidebarView: {
						viewName: "Sidebar",
						viewLevel: 1,
						controlAggregation: "masterPages"
					}
				}
			}
		},

		init: function () {
			// call overwritten init (calls createContent)
			UIComponent.prototype.init.apply(this, arguments);

			// set i18n model
			var oI18nModel = new ResourceModel({
				bundleName: "summer.camp.judge.i18n.i18n"
			});
			this.setModel(oI18nModel, "i18n");
			
			// set tasks model
			var aTasks = [
				{
					id: 0,
					name: 'Task 0',
					description: 'Description 0',
					linkToDocument: 'https://arena.maycamp.com/problems'
				},
				{
					id: 1,
					name: 'Task 1',
					description: 'Description 1',
					linkToDocument: 'https://arena.maycamp.com/problems'
				},
				{
					id: 2,
					name: 'Task 2',
					description: 'Description 2',
					linkToDocument: 'https://arena.maycamp.com/problems'
				}
			];
			
			var oTasksModel = new JSONModel('api/v1/tasks');
			this.setModel(oTasksModel, 'Tasks');
			
			this._router = this.getRouter();

			// initialize the router
			this._router.initialize();
			
			this._router.navTo('tasks');
		},

		createContent: function () {
			// create root view
			return sap.ui.view({
				viewName: "summer.camp.judge.view.App",
				type: "XML"
			});
		}
	});
});