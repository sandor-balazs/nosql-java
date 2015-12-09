'use strict';

angular.module('ontimeApp')
    .controller('DepartmentController', function ($scope, $state, Department) {

        $scope.departments = [];
        $scope.loadAll = function() {
            Department.query(function(result) {
               $scope.departments = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.department = {
                name: null,
                id: null
            };
        };
    });
