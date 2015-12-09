'use strict';

angular.module('ontimeApp')
    .controller('DepartmentDetailController', function ($scope, $rootScope, $stateParams, entity, Department, Location) {
        $scope.department = entity;
        $scope.load = function (id) {
            Department.get({id: id}, function(result) {
                $scope.department = result;
            });
        };
        var unsubscribe = $rootScope.$on('ontimeApp:departmentUpdate', function(event, result) {
            $scope.department = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
