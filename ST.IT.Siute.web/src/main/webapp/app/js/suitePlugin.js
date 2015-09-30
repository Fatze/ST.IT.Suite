// create our angular module and tell angular what route(s) it will handle
var suitePlugin = angular.module('suite_plugin', ['hawtioCore'])
        .config(function ($routeProvider) {
            $routeProvider.
                    when('/suite_plugin', {
                        templateUrl: '../hawtio/suite-plugin/app/html/simple.html'
                    });
        })


suitePlugin.run(function (workspace, viewRegistry, layoutFull) {
    viewRegistry["suite_plugin"] = layoutFull;
    workspace.topLevelTabs.push({
        id: "suite",
        content: "ST.I.Suite",
        title: "Suite plugin loaded dynamically",
        isValid: function () {
            return true;
        },
        href: function () {
            return "#/suite_plugin";
        },
        isActive: function () {
            return workspace.isLinkActive("suite_plugin");
        }
    });
});

// tell the hawtio plugin loader about our plugin so it can be
// bootstrapped with the rest of angular
hawtioPluginLoader.addModule('suite_plugin');

var ThreadController = function ($scope, jolokia) {
    $scope.threads;
    $scope.value;
    Core.register(jolokia, $scope, {
        //type: 'read', mbean: 'st.by:type=service,name=myService',suite:type=plugin,name=suite-plugin"
        type: 'read', mbean: 'suite:type=plugin,name=suite-plugin',
        arguments: []
    }, onSuccess(render)) ;

    // update display of metric
    function render(response) {
        $scope.value = response.valueOf();
        $scope.threads = response.value['Threads'];
        $scope.$apply();
    }

    function OnError11(response) {
        $scope.value = response.valueOf();
        $scope.threads = response;
        $scope.$apply();
    }

}
var TabsDemoCtrl = function ($scope, jolokia) {
}