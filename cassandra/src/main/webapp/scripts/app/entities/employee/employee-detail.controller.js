'use strict';

angular.module('ontimeApp')
    .controller('EmployeeDetailController', function ($scope, $rootScope, $stateParams, entity, Employee) {
        $scope.employee = entity;
        $scope.load = function (id) {
            Employee.get({id: id}, function(result) {
                $scope.employee = result;
            });
        };
        var unsubscribe = $rootScope.$on('ontimeApp:employeeUpdate', function(event, result) {
            $scope.employee = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
