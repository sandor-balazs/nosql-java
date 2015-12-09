'use strict';

angular.module('ontimeApp')
    .controller('EmployeeController', function ($scope, $state, Employee) {

        $scope.employees = [];
        $scope.loadAll = function() {
            Employee.query(function(result) {
               $scope.employees = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.employee = {
                firstName: null,
                lastName: null,
                userId: null,
                phone: null,
                email: null,
                id: null
            };
        };
    });
